package com.chipichipi.dobedobe.feature.setting

sealed interface SettingUiState {
    data object Loading : SettingUiState

    data class Success(
        val isGoalNotificationEnabled: Boolean,
    ) : SettingUiState
}
