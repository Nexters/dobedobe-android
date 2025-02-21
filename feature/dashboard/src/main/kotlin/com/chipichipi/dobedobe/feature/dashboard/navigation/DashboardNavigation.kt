package com.chipichipi.dobedobe.feature.dashboard.navigation

import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
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

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.dashboardScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    navigateToAddGoal: () -> Unit,
    navigateToGoalDetail: (Long) -> Unit,
    navigateToSearchGoal: () -> Unit,
    navigateToSetting: () -> Unit,
) {
    composable<DashboardRoute> {
        DashboardRoute(
            onShowSnackbar = onShowSnackbar,
            bottomSheetScaffoldState = bottomSheetScaffoldState,
            navigateToAddGoal = navigateToAddGoal,
            navigateToGoalDetail = navigateToGoalDetail,
            navigateToSetting = navigateToSetting,
            navigateToSearchGoal = navigateToSearchGoal,
        )
    }
}
