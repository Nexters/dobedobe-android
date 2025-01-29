package com.chipichipi.dobedobe.feature.dashboard.model

sealed interface DashboardEditOptionsDialogState {
    data object NotShown : DashboardEditOptionsDialogState

    data class Shown(
        val id: Int,
    ) : DashboardEditOptionsDialogState
}
