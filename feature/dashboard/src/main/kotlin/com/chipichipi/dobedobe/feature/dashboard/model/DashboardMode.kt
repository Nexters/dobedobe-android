package com.chipichipi.dobedobe.feature.dashboard.model

enum class DashboardMode {
    VIEW,
    EDIT,
    ;

    val isViewMode: Boolean
        get() = this == VIEW

    val isEditMode: Boolean
        get() = this == EDIT
}
