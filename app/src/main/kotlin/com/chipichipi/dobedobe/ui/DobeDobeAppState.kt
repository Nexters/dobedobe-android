package com.chipichipi.dobedobe.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
internal fun rememberDobeDobeAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): DobeDobeAppState {
    return remember(
        coroutineScope,
        navController,
    ) {
        DobeDobeAppState(
            coroutineScope = coroutineScope,
            navController = navController,
        )
    }
}

@Stable
class DobeDobeAppState(
    coroutineScope: CoroutineScope,
    val navController: NavHostController,
) {
    fun navigateToBack(from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.popBackStack()
        }
    }
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED
