package com.chipichipi.dobedobe.feature.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chipichipi.dobedobe.core.data.repository.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class SettingViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    val settingUiState = userRepository.userData
        .map { SettingUiState.Success(it.isGoalNotificationEnabled) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SettingUiState.Loading,
        )

    fun setGoalNotificationEnabled(checked: Boolean) {
        viewModelScope.launch {
            userRepository.setGoalNotificationEnabled(checked)
        }
    }
}
