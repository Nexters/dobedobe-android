package com.chipichipi.dobedobe.feature.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chipichipi.dobedobe.core.data.repository.GoalRepository
import com.chipichipi.dobedobe.core.data.repository.UserRepository
import com.chipichipi.dobedobe.core.model.DashboardPhoto
import com.chipichipi.dobedobe.core.model.Goal
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoConfig
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

    val uiState: StateFlow<DashboardUiState> = combine(
        fakeDashboardPhotoState,
        isSystemNotificationDialogDisabledFlow,
        goalRepository.getSortedGoals(),
    ) { photoState, isSystemNotificationDialogDisabled, goals ->
        val dashboardPhotoStates = DashboardPhotoConfig.entries.map { config ->
            val photo = photoState.find { it.id == config.id }

            DashboardPhotoState(
                config = config,
                url = photo?.url.orEmpty(),
            )
        }

        DashboardUiState.Success(
            dashboardPhotoStates,
            isSystemNotificationDialogDisabled,
            goals,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DashboardUiState.Loading,
    )

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

    fun toggleGoalCompletion(goal: Goal) {
        viewModelScope.launch {
            goalRepository.toggleCompletion(goal.id)
                .onFailure {
                    // TODO : Error 처리
                    Log.e("DashboardViewModel", "Fail to toggle Goal", it)
                }
        }
    }
}
