package com.chipichipi.dobedobe.feature.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.icon.DobeDobeIcons
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.core.model.Goal
import com.chipichipi.dobedobe.feature.dashboard.preview.GoalPreviewParameterProvider
import com.chipichipi.dobedobe.feature.goal.component.GoalRow
import com.chipichipi.dobedobe.feature.goal.component.GoalSearchBar
import kotlinx.datetime.Instant

@Composable
internal fun GoalBottomSheetContent(
    isExpanded: Boolean,
    goals: List<Goal>,
    onGoalToggled: (Long) -> Unit,
    onGoalClicked: (Long) -> Unit,
    onAddGoalClicked: () -> Unit,
    onTapSearchBar: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .imePadding(),
    ) {
        Column {
            GoalBottomSheetHeader(onAddGoalClicked)
            GoalBottomSheetBody(
                goals = goals,
                onGoalToggled = onGoalToggled,
                onGoalClicked = onGoalClicked,
            )
            GoalSearchBar(
                visible = isExpanded,
                onTapSearchBar = onTapSearchBar,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DobeDobeTheme.colors.white)
                    .padding(horizontal = 20.dp, vertical = 10.dp),
            )
        }
    }
}

@Composable
private fun GoalBottomSheetHeader(
    onAddGoalClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.feature_dashboard_goal_bottom_sheet_title),
            style = DobeDobeTheme.typography.heading2,
            color = DobeDobeTheme.colors.gray900,
        )

        IconButton(
            modifier = Modifier
                .size(width = 72.dp, height = 32.dp),
            colors = IconButtonDefaults.iconButtonColors().copy(
                containerColor = DobeDobeTheme.colors.green3,
                contentColor = DobeDobeTheme.colors.white,
            ),
            onClick = onAddGoalClicked,
        ) {
            Icon(
                painter = painterResource(DobeDobeIcons.Add),
                contentDescription = "add goal",
                tint = Color.Unspecified,
            )
        }
    }
}

@Composable
private fun GoalSearchBar(
    visible: Boolean,
    onTapSearchBar: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (visible) {
        Column {
            HorizontalDivider(color = DobeDobeTheme.colors.gray200, thickness = 1.dp)
            GoalSearchBar(
                enabled = false,
                onTapSearchBar = onTapSearchBar,
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun ColumnScope.GoalBottomSheetBody(
    goals: List<Goal>,
    onGoalToggled: (Long) -> Unit,
    onGoalClicked: (Long) -> Unit,
) {
    if (goals.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.feature_dashboard_goal_bottom_sheet_empty_message),
                style = DobeDobeTheme.typography.body1,
                color = DobeDobeTheme.colors.gray500,
                textAlign = TextAlign.Center,
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(
                horizontal = 24.dp,
                vertical = 15.dp,
            ),
        ) {
            items(goals) { goal ->
                GoalRow(
                    goal = goal,
                    onToggleCompleted = { onGoalToggled(goal.id) },
                    onClick = { onGoalClicked(goal.id) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GoalBottomSheetContentPreview(
    @PreviewParameter(GoalPreviewParameterProvider::class) pGoals: List<Goal>,
) {
    var goals by remember { mutableStateOf(pGoals) }
    val onGoalDone: (Long) -> Unit = {
        goals = goals.map { goal ->
            if (goal.id == it) {
                return@map if (goal.isCompleted) {
                    goal.copy(isCompleted = false)
                } else {
                    goal.copy(isCompleted = true, completedAt = Instant.DISTANT_FUTURE)
                }
            }
            goal
        }
    }
    GoalBottomSheetContent(
        isExpanded = true,
        goals = goals,
        onGoalToggled = onGoalDone,
        onGoalClicked = {},
        onTapSearchBar = {},
        onAddGoalClicked = {},
    )
}
