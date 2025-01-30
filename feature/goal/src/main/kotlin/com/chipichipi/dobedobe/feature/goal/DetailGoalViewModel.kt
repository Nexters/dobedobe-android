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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailGoalViewModel(
    savedStateHandle: SavedStateHandle,
    private val goalRepository: GoalRepository,
) : ViewModel() {
    val uiState: StateFlow<DetailGoalUiState> = savedStateHandle.toRoute<GoalRoute.Detail>()
        .let { route -> goalRepository.getGoal(route.id) }
        .map(DetailGoalUiState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = DetailGoalUiState.Loading,
        )

    private val _navigateToBackEvent = Channel<Unit>(capacity = Channel.BUFFERED)
    val navigateToBackEvent: Flow<Unit> = _navigateToBackEvent.receiveAsFlow()

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
            goalRepository.removeGoal(id).onSuccess {
                _navigateToBackEvent.send(Unit)
            }
        }
    }
}
