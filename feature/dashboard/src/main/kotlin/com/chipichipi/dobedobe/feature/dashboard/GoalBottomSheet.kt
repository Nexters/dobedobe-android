package com.chipichipi.dobedobe.feature.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.icon.DobeDobeIcons
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.core.model.Goal
import com.chipichipi.dobedobe.feature.dashboard.preview.GoalPreviewParameterProvider
import com.chipichipi.dobedobe.feature.goal.component.GoalRow
import kotlinx.datetime.Instant

@Composable
internal fun GoalBottomSheetContent(
    goals: List<Goal>,
    onGoalToggled: (Long) -> Unit,
    onGoalClicked: (Long) -> Unit,
    onAddGoalClicked: () -> Unit,
) {
    Column {
        GoalBottomSheetHeader(onAddGoalClicked)
        Spacer(modifier = Modifier.height(15.dp))
        GoalBottomSheetBody(
            goals = goals,
            onGoalToggled = onGoalToggled,
            onGoalClicked = onGoalClicked,
        )
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
            color = DobeDobeTheme.colors.gray900
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
                tint = Color.Unspecified
            )
        }
    }
}

@Composable
private fun GoalBottomSheetBody(
    goals: List<Goal>,
    onGoalToggled: (Long) -> Unit,
    onGoalClicked: (Long) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .requiredHeightIn(min = 200.dp)
            .fillMaxSize(),
        // TODO: 최소 높이 조절 필요
        verticalArrangement = Arrangement.spacedBy(18.dp),
        contentPadding = PaddingValues(horizontal = 24.dp),
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
        goals = goals,
        onGoalToggled = onGoalDone,
        onGoalClicked = {},
        onAddGoalClicked = {},
    )
}
