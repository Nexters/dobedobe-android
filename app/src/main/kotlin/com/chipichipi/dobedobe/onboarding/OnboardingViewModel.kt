package com.chipichipi.dobedobe.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chipichipi.dobedobe.core.data.repository.UserRepository
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    fun completeOnboarding() {
        viewModelScope.launch {
            userRepository.completeOnBoarding()
        }
    }
}
