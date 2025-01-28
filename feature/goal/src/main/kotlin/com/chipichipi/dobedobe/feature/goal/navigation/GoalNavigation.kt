package com.chipichipi.dobedobe.feature.goal.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.chipichipi.dobedobe.feature.goal.AddGoalRoute

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
        val id = backStackEntry.toRoute<GoalRoute.Detail>().id

        // TODO: 임시 확인용, Goal Detail Screen 구현
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "Goal Detail: $id")
        }
    }
}
