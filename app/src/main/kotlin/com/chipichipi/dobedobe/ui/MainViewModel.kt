package com.chipichipi.dobedobe.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chipichipi.dobedobe.core.data.repository.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    userRepository: UserRepository,
) : ViewModel() {
    val mainUiState: StateFlow<MainUiState> =
        userRepository.userData
            .map { user ->
                if (user.isOnboardingCompleted) {
                    MainUiState.Success(user)
                } else {
                    MainUiState.Onboarding
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = MainUiState.Loading,
            )
}
