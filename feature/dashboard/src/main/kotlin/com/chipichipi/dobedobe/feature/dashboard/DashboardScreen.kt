package com.chipichipi.dobedobe.feature.dashboard

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeBottomSheetScaffold
import com.chipichipi.dobedobe.feature.dashboard.preview.GoalPreviewParameterProvider
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
) {
    val bottomSheetScaffoldState =
        rememberBottomSheetScaffoldState(
            bottomSheetState =
                rememberStandardBottomSheetState(
                    initialValue = SheetValue.PartiallyExpanded,
                ),
        )

    DobeDobeBottomSheetScaffold(
        modifier = modifier,
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            GoalBottomSheetContent(
                goals = GoalPreviewParameterProvider.fakeGoals(20),
                // TODO 임시 goal 데이터
                onGoalDone = {},
                onGoalClicked = {},
            )
        },
        sheetPeekHeight = 200.dp,
        // TODO 임시 peekHeight 값
    ) {
        Text("Dashboard")
    }
}

@Composable
@Preview
private fun DashboardScreenPreview() {
    DashboardScreen(
        onShowSnackbar = { _, _ -> false },
    )
}
