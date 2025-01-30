package com.chipichipi.dobedobe.feature.goal.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.chipichipi.dobedobe.feature.goal.AddGoalRoute
import com.chipichipi.dobedobe.feature.goal.DetailGoalRoute

fun NavController.navigateToAddGoal(
    navOptions: NavOptions? = null,
) = navigate(GoalRoute.Add, navOptions)

fun NavController.navigateToGoalDetail(
    id: Long,
    navOptions: NavOptions? = null,
) = navigate(GoalRoute.Detail(id), navOptions)

fun NavGraphBuilder.goalGraph(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToBack: () -> Unit,
) {
    composable<GoalRoute.Add> {
        AddGoalRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToBack = navigateToBack,
        )
    }

    composable<GoalRoute.Detail> { backStackEntry ->
        DetailGoalRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToBack = navigateToBack,
        )
    }
}
