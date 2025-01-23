package com.chipichipi.dobedobe.feature.dashboard

import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoState

sealed interface DashboardUiState {
    data object Loading : DashboardUiState

    data class Success(
        val photoState: List<DashboardPhotoState>,
    ) : DashboardUiState

    data object Error : DashboardUiState
}
