package com.chipichipi.dobedobe.feature.goal

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.placeCursorAtEnd
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.chipichipi.dobedobe.core.data.repository.GoalRepository
import com.chipichipi.dobedobe.core.model.Goal
import com.chipichipi.dobedobe.feature.goal.navigation.GoalRoute
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class EditGoalViewModel(
    savedStateHandle: SavedStateHandle,
    private val goalRepository: GoalRepository,
) : ViewModel() {
    val goalTitleDraft = TextFieldState("")
    val goalValidResult by derivedStateOf {
        goalTitleDraft.text.toString().let(Goal::validateTitle)
    }

    private val isGoalChanged
        get() =
            when (val uiState = uiState.value) {
                is EditGoalUiState.Success -> uiState.goal.title != goalTitleDraft.text.toString()
                is EditGoalUiState.Loading, EditGoalUiState.Error -> false
            }

    val uiState: StateFlow<EditGoalUiState> = savedStateHandle.getGoalFlow()
        .map(EditGoalUiState::Success)
        .onEach {
            goalTitleDraft.edit {
                delete(0, length)
                placeCursorAtEnd()
                append(it.goal.title)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = EditGoalUiState.Loading,
        )

    private val _uiEvent: Channel<EditGoalUiEvent> =
        Channel<EditGoalUiEvent>(capacity = Channel.BUFFERED)
    val uiEvent: Flow<EditGoalUiEvent> = _uiEvent.receiveAsFlow()

    fun saveGoalTitle() {
        if (goalValidResult.isValid().not()) return

        if (isGoalChanged.not()) {
            viewModelScope.launch {
                _uiEvent.send(EditGoalUiEvent.NotChange)
            }
            return
        }

        val uiState = uiState.value
        if (uiState is EditGoalUiState.Success) {
            viewModelScope.launch {
                goalRepository.changeGoalTitle(
                    uiState.goal.id,
                    goalTitleDraft.text.toString(),
                ).onSuccess {
                    _uiEvent.send(EditGoalUiEvent.Change)
                }
            }
        }
    }

    private fun SavedStateHandle.getGoalFlow(): Flow<Goal> {
        return toRoute<GoalRoute.Edit>()
            .let { route -> goalRepository.getGoal(route.id) }
            .filterNotNull()
    }
}

sealed interface EditGoalUiState {
    data object Loading : EditGoalUiState

    data class Success(
        val goal: Goal,
    ) : EditGoalUiState

    data object Error : EditGoalUiState
}

sealed interface EditGoalUiEvent {
    data object Change : EditGoalUiEvent

    data object NotChange : EditGoalUiEvent
}
