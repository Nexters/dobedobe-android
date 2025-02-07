package com.chipichipi.dobedobe.feature.dashboard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.chipichipi.dobedobe.feature.dashboard.DashboardRoute
import kotlinx.serialization.Serializable

@Serializable
data object DashboardRoute

fun NavController.navigateToDashboard(
    navOptions: NavOptions,
) = navigate(route = DashboardRoute, navOptions)

fun NavGraphBuilder.dashboardScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToAddGoal: () -> Unit,
    navigateToGoalDetail: (Long) -> Unit,
    navigateToGoalSearch: () -> Unit,
    navigateToSetting: () -> Unit,
) {
    composable<DashboardRoute> {
        DashboardRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToAddGoal = navigateToAddGoal,
            navigateToGoalDetail = navigateToGoalDetail,
            navigateToSetting = navigateToSetting,
            navigateToGoalSearch = navigateToGoalSearch,
        )
    }
}
