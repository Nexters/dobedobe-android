package com.chipichipi.dobedobe.feature.dashboard.model

sealed interface DashboardModeState {
    data object View : DashboardModeState

    data class Edit(
        val drafts: List<DashboardPhotoState> = emptyList(),
    ) : DashboardModeState
}
