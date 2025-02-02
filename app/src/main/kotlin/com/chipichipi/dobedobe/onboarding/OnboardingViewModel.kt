package com.chipichipi.dobedobe.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chipichipi.dobedobe.core.data.repository.GoalRepository
import com.chipichipi.dobedobe.core.data.repository.UserRepository
import com.chipichipi.dobedobe.core.model.CharacterType
import com.chipichipi.dobedobe.core.model.Goal
import com.chipichipi.dobedobe.core.model.GoalTitleValidResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val userRepository: UserRepository,
    private val goalRepository: GoalRepository,
) : ViewModel() {
    private val title: MutableStateFlow<String> = MutableStateFlow("")
    val titleValidResult: StateFlow<GoalTitleValidResult> =
        title.map(Goal::validateTitle)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = GoalTitleValidResult.Empty,
            )
    private val _selectedCharacter: MutableStateFlow<CharacterType> = MutableStateFlow(CharacterType.Bird)
    val selectedCharacter = _selectedCharacter.asStateFlow()

    fun changeGoalTitle(newTitle: String) {
        title.value = newTitle
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            if (titleValidResult.value.isValid()) {
                goalRepository.addGoal(title.value)
                    .onSuccess {
                        userRepository.completeOnBoarding()
                    }
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
