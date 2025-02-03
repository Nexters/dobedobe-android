package com.chipichipi.dobedobe.onboarding

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.chipichipi.dobedobe.core.data.repository.GoalRepository
import com.chipichipi.dobedobe.core.data.repository.UserRepository
import com.chipichipi.dobedobe.core.model.CharacterType
import com.chipichipi.dobedobe.onboarding.navigation.OnboardingSelectCharacterRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class OnboardingSelectCharacterViewModel(
    private val userRepository: UserRepository,
    private val goalRepository: GoalRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val route = savedStateHandle.toRoute<OnboardingSelectCharacterRoute>()

    private val _selectedCharacter: MutableStateFlow<CharacterType> =
        MutableStateFlow(CharacterType.Bird)
    val selectedCharacter = _selectedCharacter.asStateFlow()

    fun completeOnboarding() {
        viewModelScope.launch {
            goalRepository.addGoal(route.goalTitle)
                .onSuccess {
                    userRepository.completeOnBoarding()
                }
        }
    }

    fun toggleCharacter() {
        viewModelScope.launch {
            _selectedCharacter.value = if (_selectedCharacter.value == CharacterType.Bird) {
                CharacterType.Rabbit
            } else {
                CharacterType.Bird
            }
        }
    }
}
