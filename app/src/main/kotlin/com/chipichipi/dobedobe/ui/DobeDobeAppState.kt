package com.chipichipi.dobedobe.ui

import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun rememberDobeDobeAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    bottomSheetScaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded,
        ),
    ),
    navController: NavHostController = rememberNavController(),
): DobeDobeAppState {
    return remember(
        coroutineScope,
        navController,
    ) {
        DobeDobeAppState(
            coroutineScope = coroutineScope,
            bottomSheetScaffoldState = bottomSheetScaffoldState,
            navController = navController,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Stable
class DobeDobeAppState(
    val coroutineScope: CoroutineScope,
    val bottomSheetScaffoldState: BottomSheetScaffoldState,
    val navController: NavHostController,
) {
    fun navigateToBack(from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.popBackStack()
        }
    }

    fun partiallyExpand() {
        coroutineScope.launch {
            bottomSheetScaffoldState.bottomSheetState.partialExpand()
        }
    }
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED
