package com.chipichipi.dobedobe.feature.goal

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chipichipi.dobedobe.core.data.repository.GoalRepository
import com.chipichipi.dobedobe.core.data.repository.UserRepository
import com.chipichipi.dobedobe.core.model.CharacterType
import com.chipichipi.dobedobe.core.model.Goal
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddGoalViewModel(
    private val goalRepository: GoalRepository,
    userRepository: UserRepository,
) : ViewModel() {
    val goalTitle = TextFieldState()
    val goalValidResult by derivedStateOf { goalTitle.text.toString().let(Goal::validateTitle) }

    val characterType: StateFlow<CharacterType> = userRepository.userData.map {
        it.characterType
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CharacterType.Rabbit,
    )

    private val _addGoalEvent = Channel<Unit>(capacity = Channel.BUFFERED)
    val addGoalEvent: Flow<Unit> = _addGoalEvent.receiveAsFlow()

    fun addGoal() {
        viewModelScope.launch {
            if (goalValidResult.isValid()) {
                goalRepository.addGoal(goalTitle.text.toString()).onSuccess {
                    _addGoalEvent.send(Unit)
                }
            }
        }
    }
}
