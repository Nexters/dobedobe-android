package com.chipichipi.dobedobe.feature.goal

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.chipichipi.dobedobe.core.data.repository.GoalRepository
import com.chipichipi.dobedobe.core.model.Goal
import com.chipichipi.dobedobe.feature.goal.navigation.GoalRoute
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class DetailGoalViewModel(
    savedStateHandle: SavedStateHandle,
    private val goalRepository: GoalRepository,
) : ViewModel() {
    private val originalGoal: MutableStateFlow<Goal?> = MutableStateFlow(null)
    private val goalTitleDraft: MutableStateFlow<String> = MutableStateFlow("")

    val uiState: StateFlow<DetailGoalUiState> = savedStateHandle.getGoalFlow()
        .combine(goalTitleDraft) { goal, draftTitle ->
            DetailGoalUiState.Success(goal, draftTitle)
        }
        .onEach { changeGoalTitleIfNeeded(it.goal, it.draftTitle) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = DetailGoalUiState.Loading,
        )

    val isGoalChanged: StateFlow<Boolean> =
        combine(originalGoal, uiState) { original, current ->
            if (current is DetailGoalUiState.Success) {
                original != current.goal
            } else {
                false
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = false,
            )

    private val _deleteGoalEvent = Channel<Unit>(capacity = Channel.BUFFERED)
    val deleteGoalEvent: Flow<Unit> = _deleteGoalEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            val initialGoal = savedStateHandle.getGoalFlow().first()
            originalGoal.value = initialGoal
        }
    }

    fun changeGoalTitle(title: String) {
        goalTitleDraft.value = title
    }

    fun togglePinned(id: Long) {
        viewModelScope.launch {
            goalRepository.togglePin(id)
        }
    }

    fun toggleCompleted(id: Long) {
        viewModelScope.launch {
            goalRepository.toggleCompletion(id)
        }
    }

    fun removeGoal(id: Long) {
        viewModelScope.launch {
            goalRepository.removeGoal(id)
                .onSuccess {
                    _deleteGoalEvent.send(Unit)
                }
        }
    }

    private fun SavedStateHandle.getGoalFlow(): Flow<Goal> {
        return toRoute<GoalRoute.Detail>()
            .let { route -> goalRepository.getGoal(route.id) }
            .filterNotNull()
    }

    private fun changeGoalTitleIfNeeded(goal: Goal, newTitle: String) {
        if (Goal.isValid(newTitle).not()) return

        if (goal.title != newTitle) {
            viewModelScope.launch {
                goalRepository.changeGoalTitle(goal.id, newTitle)
            }
        }
    }
}
