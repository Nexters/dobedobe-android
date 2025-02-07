package com.chipichipi.dobedobe.feature.goal.navigation

import androidx.activity.compose.BackHandler
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.chipichipi.dobedobe.feature.goal.AddGoalRoute
import com.chipichipi.dobedobe.feature.goal.DetailGoalRoute
import com.chipichipi.dobedobe.feature.goal.GoalSnackBarType
import com.chipichipi.dobedobe.feature.goal.SearchGoalRoute

fun NavController.navigateToAddGoal(
    navOptions: NavOptions? = null,
) = navigate(GoalRoute.Add, navOptions)

fun NavController.navigateToGoalDetail(
    id: Long,
    navOptions: NavOptions? = null,
) = navigate(GoalRoute.Detail(id), navOptions)

fun NavController.navigateToSearchGoal(
    navOptions: NavOptions? = null,
) = navigate(GoalRoute.Search, navOptions)

fun NavGraphBuilder.goalGraph(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    sendSnackBarEvent: (GoalSnackBarType) -> Unit,
    navigateToBack: (NavBackStackEntry) -> Unit,
    navigateToDashboard: () -> Unit,
    navigateToGoalDetail: (Long) -> Unit,
) {
    composable<GoalRoute.Add> { from ->
        AddGoalRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToBack = { navigateToBack(from) },
            sendSnackBarEvent = sendSnackBarEvent,
        )
    }

    composable<GoalRoute.Detail> { from ->
        DetailGoalRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToBack = { navigateToBack(from) },
            sendSnackBarEvent = sendSnackBarEvent,
        )
    }

    composable<GoalRoute.Search> { from ->
        BackHandler {
            navigateToDashboard()
        }
        SearchGoalRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToGoalDetail = navigateToGoalDetail,
            navigateToBack = navigateToDashboard,
        )
    }
}