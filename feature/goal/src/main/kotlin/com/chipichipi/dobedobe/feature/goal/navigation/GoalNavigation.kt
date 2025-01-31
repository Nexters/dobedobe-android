package com.chipichipi.dobedobe.feature.goal.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.chipichipi.dobedobe.feature.goal.AddGoalRoute
import com.chipichipi.dobedobe.feature.goal.DetailGoalRoute
import com.chipichipi.dobedobe.feature.goal.GoalSnackBarType

fun NavController.navigateToAddGoal(
    navOptions: NavOptions? = null,
) = navigate(GoalRoute.Add, navOptions)

fun NavController.navigateToGoalDetail(
    id: Long,
    navOptions: NavOptions? = null,
) = navigate(GoalRoute.Detail(id), navOptions)

fun NavGraphBuilder.goalGraph(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    saveSnackBarEvent: (GoalSnackBarType) -> Unit,
    navigateToBack: () -> Unit,
) {
    composable<GoalRoute.Add> {
        AddGoalRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToBack = navigateToBack,
            saveSnackBarEvent = saveSnackBarEvent,
        )
    }

    composable<GoalRoute.Detail> {
        DetailGoalRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToBack = navigateToBack,
            saveSnackBarEvent = saveSnackBarEvent,
        )
    }
}
