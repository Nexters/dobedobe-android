package com.chipichipi.dobedobe.feature.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.model.fakeGoals
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun DashboardRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = koinViewModel(),
) {
    DashboardScreen(
        modifier = modifier,
        onShowSnackbar = onShowSnackbar,
    )
}

@Composable
private fun DashboardScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Text(
            "Dashboard",
        )
        GoalBottomSheet(
            sheetPeekHeight = 200.dp, // TODO 임시 peekHeight 값
            goals = fakeGoals(20), // TODO 임시 goal 데이터
            onGoalItemDone = {},
            onGoalItemClick = {},
        )
    }
}
