package com.chipichipi.dobedobe.feature.goal

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.chipichipi.dobedobe.core.data.repository.GoalRepository
import com.chipichipi.dobedobe.core.data.repository.UserRepository
import com.chipichipi.dobedobe.core.model.CharacterType
import com.chipichipi.dobedobe.core.model.Goal
import com.chipichipi.dobedobe.feature.goal.navigation.GoalRoute
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class DetailGoalViewModel(
    savedStateHandle: SavedStateHandle,
    private val goalRepository: GoalRepository,
    userRepository: UserRepository,
) : ViewModel() {
    private var originalGoal: Goal? = null

    val uiState: StateFlow<DetailGoalUiState> = savedStateHandle.getGoalFlow()
        .combine(userRepository.userData) { goal, user ->
            DetailGoalUiState.Success(
                goal = goal,
                characterType = user.characterType,
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = DetailGoalUiState.Loading,
        )

    val isGoalChanged
        get() =
            when (val uiState = uiState.value) {
                is DetailGoalUiState.Success -> uiState.goal != originalGoal
                is DetailGoalUiState.Loading, DetailGoalUiState.Error -> false
            }

    private val _goalUiEvent = Channel<DetailGoalUiEvent>(capacity = Channel.CONFLATED)
    val goalUiEvent: Flow<DetailGoalUiEvent> = _goalUiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            originalGoal = savedStateHandle.getGoalFlow().first()
        }
        observeGoalCompletion()
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
                    _goalUiEvent.send(DetailGoalUiEvent.Delete)
                }
        }
    }

    private fun observeGoalCompletion() {
        viewModelScope.launch {
            uiState
                .mapNotNull { (it as? DetailGoalUiState.Success)?.goal?.isCompleted }
                .distinctUntilChanged()
                .drop(1)
                .collectLatest { isCompleted ->
                    if (isCompleted) {
                        _goalUiEvent.send(DetailGoalUiEvent.CompleteGoal)
                    } else {
                        _goalUiEvent.send(DetailGoalUiEvent.UnDoGoal)
                    }
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
        val characterType: CharacterType,
    ) : DetailGoalUiState

    data object Error : DetailGoalUiState

    val isSuccess: Boolean
        get() = this is Success
}

sealed interface DetailGoalUiEvent {
    data object Delete : DetailGoalUiEvent

    data object CompleteGoal : DetailGoalUiEvent

    data object UnDoGoal : DetailGoalUiEvent
}
