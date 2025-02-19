package com.chipichipi.dobedobe.feature.goal

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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class DetailGoalViewModel(
    savedStateHandle: SavedStateHandle,
    private val goalRepository: GoalRepository,
) : ViewModel() {
    private var originalGoal: Goal? = null

    val uiState: StateFlow<DetailGoalUiState> = savedStateHandle.getGoalFlow()
        .map(DetailGoalUiState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = DetailGoalUiState.Loading,
        )

    val isGoalChanged by derivedStateOf {
        when (val uiState = uiState.value) {
            is DetailGoalUiState.Success -> uiState.goal != originalGoal
            is DetailGoalUiState.Loading, DetailGoalUiState.Error -> false
        }
    }

    private val _deleteGoalEvent = Channel<Unit>(capacity = Channel.BUFFERED)
    val deleteGoalEvent: Flow<Unit> = _deleteGoalEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            originalGoal = savedStateHandle.getGoalFlow().first()
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

    private fun SavedStateHandle.getGoalFlow(): Flow<Goal> {
        return toRoute<GoalRoute.Detail>()
            .let { route -> goalRepository.getGoal(route.id) }
            .filterNotNull()
    }
}

sealed interface DetailGoalUiState {
    data object Loading : DetailGoalUiState

    data class Success(
        val goal: Goal,
    ) : DetailGoalUiState

    data object Error : DetailGoalUiState

    val isSuccess: Boolean
        get() = this is Success
}
