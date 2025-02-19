package com.chipichipi.dobedobe.feature.goal

import com.chipichipi.dobedobe.core.model.GoalTitleValidResult

fun GoalTitleValidResult.addGoalErrorMessage(): Int? = when (this) {
    GoalTitleValidResult.Valid, GoalTitleValidResult.Empty -> null
    GoalTitleValidResult.Blank -> R.string.feature_goal_title_error_message_blank
    GoalTitleValidResult.TooLong -> R.string.feature_goal_title_error_message_too_long
}

fun GoalTitleValidResult.editGoalErrorMessage(): Int? = when (this) {
    GoalTitleValidResult.Valid -> null
    GoalTitleValidResult.Blank, GoalTitleValidResult.Empty -> R.string.feature_goal_title_error_message_blank
    GoalTitleValidResult.TooLong -> R.string.feature_goal_title_error_message_too_long
}
