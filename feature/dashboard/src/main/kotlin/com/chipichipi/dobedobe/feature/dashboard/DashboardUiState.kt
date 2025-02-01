package com.chipichipi.dobedobe.feature.dashboard

import com.chipichipi.dobedobe.core.model.Goal
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoState

sealed interface DashboardUiState {
    data object Loading : DashboardUiState

    data class Success(
        val photoState: List<DashboardPhotoState>,
        val isSystemNotificationDialogDisabled: Boolean,
        val goals: List<Goal>,
        val bubbleTitle: String,
    ) : DashboardUiState

    data object Error : DashboardUiState
}
