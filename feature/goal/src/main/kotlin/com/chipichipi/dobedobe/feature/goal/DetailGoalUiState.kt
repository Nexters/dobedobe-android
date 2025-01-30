package com.chipichipi.dobedobe.feature.goal

import com.chipichipi.dobedobe.core.model.Goal
import com.chipichipi.dobedobe.core.model.GoalTitleValidResult

sealed interface DetailGoalUiState {
    data object Loading : DetailGoalUiState

    data class Success(val goal: Goal) : DetailGoalUiState {
        val goalValidResult: GoalTitleValidResult = Goal.validateTitle(goal.title)
    }

    data object Error : DetailGoalUiState

    val isSuccess: Boolean
        get() = this is Success
}
