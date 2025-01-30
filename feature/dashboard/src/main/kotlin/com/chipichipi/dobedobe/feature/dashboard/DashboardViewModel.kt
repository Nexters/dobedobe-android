package com.chipichipi.dobedobe.feature.dashboard

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chipichipi.dobedobe.core.data.repository.DashboardRepository
import com.chipichipi.dobedobe.core.data.repository.GoalRepository
import com.chipichipi.dobedobe.core.data.repository.UserRepository
import com.chipichipi.dobedobe.core.model.DashboardPhoto
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardModeState
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoConfig
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

internal class DashboardViewModel(
    private val userRepository: UserRepository,
    private val goalRepository: GoalRepository,
    private val dashboardRepository: DashboardRepository,
) : ViewModel() {
    private val isSystemNotificationDialogDisabledFlow = userRepository.userData
        .map { it.isSystemNotificationDialogDisabled }
        .distinctUntilChanged()

    private val _modeState: MutableStateFlow<DashboardModeState> =
        MutableStateFlow(DashboardModeState.View)
    val modeState: StateFlow<DashboardModeState> = _modeState.asStateFlow()

    private val bubbleTitle: MutableStateFlow<String> = MutableStateFlow("")

    val uiState: StateFlow<DashboardUiState> = combine(
        dashboardRepository.getPhotos(),
        isSystemNotificationDialogDisabledFlow,
        goalRepository.getSortedGoals(),
        bubbleTitle,
    ) { photoState, isSystemNotificationDialogDisabled, goals, bubbleTitle ->
        val dashboardPhotoStates = DashboardPhotoConfig.entries.map { config ->
            val photo = photoState.find { it.id == config.id }
            val uri = photo?.uri?.let { Uri.fromFile(File(it)) } ?: Uri.EMPTY

            DashboardPhotoState(
                config = config,
                uri = uri,
            )
        }

        DashboardUiState.Success(
            photoState = dashboardPhotoStates,
            isSystemNotificationDialogDisabled = isSystemNotificationDialogDisabled,
            goals = goals,
            bubbleTitle = bubbleTitle,
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DashboardUiState.Loading,
        )

    init {
        changeBubble()
    }

    fun setGoalNotificationEnabled(enabled: Boolean) {
        viewModelScope.launch {
            userRepository.setGoalNotificationEnabled(enabled)
        }
    }

    fun disableSystemNotificationDialog() {
        viewModelScope.launch {
            userRepository.disableSystemNotificationDialog()
        }
    }

    fun toggleGoalCompletion(id: Long) {
        viewModelScope.launch {
            goalRepository.toggleCompletion(id)
                .onFailure {
                    // TODO : Error 처리
                    Log.e("DashboardViewModel", "Fail to toggle Goal", it)
                }
        }
    }

    fun toggleMode() {
        if (uiState.value is DashboardUiState.Success) {
            viewModelScope.launch {
                _modeState.update { currentState ->
                    if (currentState is DashboardModeState.View) {
                        DashboardModeState.Edit(
                            drafts = (uiState.value as DashboardUiState.Success).photoState,
                        )
                    } else {
                        DashboardModeState.View
                    }
                }
            }
        }
    }

    fun upsertPhotos(photos: List<DashboardPhoto>) {
        viewModelScope.launch {
            dashboardRepository.upsertPhotos(photos)
        }
    }

    fun deletePhotos(photos: List<DashboardPhoto>) {
        viewModelScope.launch {
            val ids = photos.map { it.id }
            dashboardRepository.deletePhotosByIds(ids)
        }
    }

    fun updatePhotoDrafts(id: Int?, uri: Uri) {
        require(_modeState.value is DashboardModeState.Edit) {
            "updatePhotoDrafts() should only be called in Edit mode"
        }

        viewModelScope.launch {
            _modeState.update { state ->
                (state as DashboardModeState.Edit).copy(
                    drafts = state.drafts.map { draft ->
                        if (draft.config.id == id) {
                            draft.copy(
                                uri = uri,
                                hasUriChanged = true,
                            )
                        } else {
                            draft
                        }
                    },
                )
            }
        }
    }

    fun changeBubble() {
        viewModelScope.launch {
            goalRepository.getTodoGoals()
                .onSuccess { goals ->
                    if (goals.isNotEmpty()) {
                        bubbleTitle.value = goals.random().title
                    }
                }
                .onFailure {
                    // TODO : Error 처리
                    Log.e("DashboardViewModel", "Fail to get random todo goal", it)
                }
        }
    }
}
