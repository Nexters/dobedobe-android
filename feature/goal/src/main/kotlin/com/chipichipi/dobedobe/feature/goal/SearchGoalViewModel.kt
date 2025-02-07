package com.chipichipi.dobedobe.feature.goal

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chipichipi.dobedobe.core.data.repository.GoalRepository
import com.chipichipi.dobedobe.core.model.Goal
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SearchGoalViewModel(
    private val goalRepository: GoalRepository,
) : ViewModel() {
    val queryState = TextFieldState()

    val uiState =
        snapshotFlow { queryState.text }
            .flatMapLatest { query ->
                goalRepository.getSortedGoals()
                    .map { goals ->
                        if (query.isBlank()) {
                            return@map SearchGoalUiState.Success(goals, emptyList())
                        }

                        val queriedGoals = goals.filter { query in it.title }
                        SearchGoalUiState.Success(goals, queriedGoals)
                    }
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, SearchGoalUiState.Loading)

    fun clearQuery() {
        queryState.edit {
            delete(0, this.length)
        }
    }
}

sealed interface SearchGoalUiState {
    data object Loading : SearchGoalUiState

    data class Success(
        val goals: List<Goal>,
        val queriedGoals: List<Goal>,
    ) : SearchGoalUiState
}

fun main() {
    println("" in "123")
}
