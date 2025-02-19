package com.chipichipi.dobedobe.feature.goal.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.chipichipi.dobedobe.feature.goal.AddGoalRoute
import com.chipichipi.dobedobe.feature.goal.DetailGoalRoute
import com.chipichipi.dobedobe.feature.goal.EditGoalRoute
import com.chipichipi.dobedobe.feature.goal.GoalSnackBarType
import com.chipichipi.dobedobe.feature.goal.SearchGoalRoute

fun NavController.navigateToAddGoal(
    navOptions: NavOptions? = null,
) = navigate(GoalRoute.Add, navOptions)

fun NavController.navigateToGoalDetail(
    id: Long,
    navOptions: NavOptions? = null,
) = navigate(GoalRoute.Detail(id), navOptions)

fun NavController.navigateToEditGoal(
    id: Long,
    navOptions: NavOptions? = null,
) = navigate(GoalRoute.Edit(id), navOptions)

fun NavController.navigateToSearchGoal(
    navOptions: NavOptions? = null,
) = navigate(GoalRoute.Search, navOptions)

fun NavGraphBuilder.goalGraph(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    sendSnackBarEvent: (GoalSnackBarType) -> Unit,
    navigateToBack: (NavBackStackEntry) -> Unit,
    navigateToGoalDetail: (Long) -> Unit,
    navigateToEditGoal: (Long) -> Unit,
) {
    composable<GoalRoute.Add> { from ->
        AddGoalRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToBack = { navigateToBack(from) },
            sendSnackBarEvent = sendSnackBarEvent,
        )
    }

    composable<GoalRoute.Detail> { from ->
        val id = from.toRoute<GoalRoute.Detail>().id

        DetailGoalRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToBack = { navigateToBack(from) },
            navigateToEditMode = { navigateToEditGoal(id) },
            sendSnackBarEvent = sendSnackBarEvent,
        )
    }

    composable<GoalRoute.Edit> { from ->
        EditGoalRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToBack = { navigateToBack(from) },
            sendSnackBarEvent = sendSnackBarEvent,
        )
    }

    composable<GoalRoute.Search> { from ->
        SearchGoalRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToGoalDetail = navigateToGoalDetail,
            navigateToBack = { navigateToBack(from) },
        )
    }
}
