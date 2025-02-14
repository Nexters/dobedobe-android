package com.chipichipi.dobedobe.feature.setting

import com.chipichipi.dobedobe.core.model.CharacterType

sealed interface SettingUiState {
    data object Loading : SettingUiState

    data class Success(
        val isGoalNotificationEnabled: Boolean,
        val characterType: CharacterType
    ) : SettingUiState
}
