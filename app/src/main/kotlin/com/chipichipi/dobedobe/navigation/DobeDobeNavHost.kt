package com.chipichipi.dobedobe.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.chipichipi.dobedobe.feature.dashboard.R
import com.chipichipi.dobedobe.feature.dashboard.navigation.DashboardRoute
import com.chipichipi.dobedobe.feature.dashboard.navigation.dashboardScreen
import com.chipichipi.dobedobe.feature.goal.GoalSnackBarType
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
    val backStackEntry by navController.currentBackStackEntryAsState()

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
            saveSnackBarEvent = navController::saveSnackBarEvent,
        )

        settingScreen(
            onShowSnackbar = onShowSnackbar,
            navigateToBack = navController::popBackStack,
        )
    }

    GoalSnackBarEffect(backStackEntry, onShowSnackbar)
}

@Composable
private fun GoalSnackBarEffect(
    backStackEntry: NavBackStackEntry?,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    if (backStackEntry == null) return
    val snackBarState = backStackEntry.savedStateHandle.getStateFlow(
        GoalSnackBarType.KEY,
        GoalSnackBarType.IDLE,
    )
    val addGoalMessage =
        stringResource(id = R.string.feature_dashboard_add_goal_snackbar_message)
    val editGoalMessage =
        stringResource(id = R.string.feature_dashboard_edit_goal_snackbar_message)
    val removeGoalMessage =
        stringResource(id = R.string.feature_dashboard_remove_goal_snackbar_message)

    LaunchedEffect(snackBarState) {
        when (snackBarState.value) {
            GoalSnackBarType.IDLE -> {}
            GoalSnackBarType.ADD -> onShowSnackbar(addGoalMessage, null)
            GoalSnackBarType.EDIT -> onShowSnackbar(editGoalMessage, null)
            GoalSnackBarType.REMOVE -> onShowSnackbar(removeGoalMessage, null)
        }
        backStackEntry.removeSnackBarEvent()
    }
}

fun NavController.saveSnackBarEvent(
    type: GoalSnackBarType,
) {
    val preBackStackEntry = previousBackStackEntry ?: return
    if (preBackStackEntry.destination.route == DashboardRoute::class.java.canonicalName) {
        preBackStackEntry.savedStateHandle[GoalSnackBarType.KEY] = type
    }
}

fun NavBackStackEntry.removeSnackBarEvent() {
    savedStateHandle.remove<GoalSnackBarType>(GoalSnackBarType.KEY)
}
