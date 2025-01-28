package com.chipichipi.dobedobe.feature.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chipichipi.dobedobe.core.data.repository.GoalRepository
import com.chipichipi.dobedobe.core.data.repository.UserRepository
import com.chipichipi.dobedobe.core.model.DashboardPhoto
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardMode
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoConfig
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// TODO : 제거 필요
private val fakeDashboardPhotoState =
    MutableStateFlow(
        listOf(
            DashboardPhoto(1, "https://picsum.photos/id/237/200/300"),
            DashboardPhoto(2, "https://picsum.photos/id/233/200/300"),
        ),
    )

internal class DashboardViewModel(
    private val userRepository: UserRepository,
    private val goalRepository: GoalRepository,
) : ViewModel() {
    private val isSystemNotificationDialogDisabledFlow = userRepository.userData
        .map { it.isSystemNotificationDialogDisabled }
        .distinctUntilChanged()

    private val mode: MutableStateFlow<DashboardMode> = MutableStateFlow(DashboardMode.VIEW)

    private val bubbleTitle: MutableStateFlow<String> = MutableStateFlow("")

    val uiState: StateFlow<DashboardUiState> = combine(
        fakeDashboardPhotoState,
        isSystemNotificationDialogDisabledFlow,
        goalRepository.getSortedGoals(),
        mode,
        bubbleTitle,
    ) { photoState, isSystemNotificationDialogDisabled, goals, mode, bubbleTitle ->
        val dashboardPhotoStates = DashboardPhotoConfig.entries.map { config ->
            val photo = photoState.find { it.id == config.id }

            DashboardPhotoState(
                config = config,
                uri = photo?.uri.orEmpty(),
            )
        }

        DashboardUiState.Success(
            photoState = dashboardPhotoStates,
            isSystemNotificationDialogDisabled = isSystemNotificationDialogDisabled,
            goals = goals,
            mode = mode,
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
        viewModelScope.launch {
            mode.update { currentMode ->
                when (currentMode) {
                    DashboardMode.VIEW -> DashboardMode.EDIT
                    DashboardMode.EDIT -> DashboardMode.VIEW
                }
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
