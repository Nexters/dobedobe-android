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
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class DetailGoalViewModel(
    savedStateHandle: SavedStateHandle,
    private val goalRepository: GoalRepository,
) : ViewModel() {
    private var originalGoal: MutableStateFlow<Goal?> = MutableStateFlow(null)

    val uiState: StateFlow<DetailGoalUiState> = savedStateHandle.toRoute<GoalRoute.Detail>()
        .let { route -> goalRepository.getGoal(route.id) }
        .mapNotNull { it?.let(DetailGoalUiState::Success) }
        .onEach {
            if (originalGoal.value == null) {
                originalGoal.value = it.goal
            }
        }
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

    fun changeGoalTitle(id: Long, title: String) {
        viewModelScope.launch {
            if (Goal.validateTitle(title).isValid()) {
                goalRepository.changeGoalTitle(id, title)
            }
        }
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
}
