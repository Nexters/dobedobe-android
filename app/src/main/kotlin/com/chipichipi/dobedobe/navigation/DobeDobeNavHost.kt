package com.chipichipi.dobedobe.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.chipichipi.dobedobe.feature.dashboard.navigation.DashboardRoute
import com.chipichipi.dobedobe.feature.dashboard.navigation.dashboardScreen
import com.chipichipi.dobedobe.feature.goal.navigation.goalGraph
import com.chipichipi.dobedobe.feature.goal.navigation.navigateToAddGoal
import com.chipichipi.dobedobe.feature.goal.navigation.navigateToGoalDetail
import com.chipichipi.dobedobe.feature.setting.navigation.navigateToSetting
import com.chipichipi.dobedobe.feature.setting.navigation.settingScreen
import com.chipichipi.dobedobe.ui.DobeDobeAppState

@Composable
internal fun DobeDobeNavHost(
    appState: DobeDobeAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = DashboardRoute,
        modifier = modifier,
    ) {
        dashboardScreen(
            onShowSnackbar = onShowSnackbar,
            navigateToAddGoal = navController::navigateToAddGoal,
            navigateToGoalDetail = navController::navigateToGoalDetail,
            navigateToSetting = navController::navigateToSetting,
        )

        goalGraph(
            onShowSnackbar = onShowSnackbar,
            navigateToBack = navController::popBackStack,
        )

        settingScreen(
            onShowSnackbar = onShowSnackbar,
            navigateToBack = navController::popBackStack,
        )
    }
}
