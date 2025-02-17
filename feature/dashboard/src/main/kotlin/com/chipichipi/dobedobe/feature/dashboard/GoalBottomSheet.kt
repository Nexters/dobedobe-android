package com.chipichipi.dobedobe.feature.dashboard

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.icon.DobeDobeIcons
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.core.designsystem.util.dropShadow
import com.chipichipi.dobedobe.core.model.Goal
import com.chipichipi.dobedobe.feature.dashboard.preview.GoalPreviewParameterProvider
import com.chipichipi.dobedobe.feature.goal.component.GoalRow
import com.chipichipi.dobedobe.feature.goal.component.GoalSearchBar
import kotlinx.datetime.Instant

@Composable
internal fun GoalBottomSheetContent(
    isNotPartiallyExpanded: Boolean,
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
                isNotPartiallyExpanded = isNotPartiallyExpanded,
                goals = goals,
                onGoalToggled = onGoalToggled,
                onGoalClicked = onGoalClicked,
                modifier = Modifier.weight(1f),
            )
            if (isNotPartiallyExpanded) {
                SearchGoalNavigationBar(
                    onTapSearchBar = onTapSearchBar,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(DobeDobeTheme.colors.white)
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                )
            }
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
            style = DobeDobeTheme.typography.heading1,
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
private fun SearchGoalNavigationBar(
    onTapSearchBar: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        HorizontalDivider(color = DobeDobeTheme.colors.gray200, thickness = 1.dp)
        GoalSearchBar(
            enabled = false,
            onTapSearchBar = onTapSearchBar,
            modifier = modifier,
        )
    }
}

@Composable
private fun GoalBottomSheetBody(
    isNotPartiallyExpanded: Boolean,
    goals: List<Goal>,
    onGoalToggled: (Long) -> Unit,
    onGoalClicked: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                vertical = 16.dp,
                horizontal = 24.dp,
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            GoalInfoCard(
                modifier = Modifier.weight(1f),
                title = "올해의 목표", // TODO :
                count = goals.size,
                icon = DobeDobeIcons.Year2025,
                dropShadowColor = Color(0xFF6CD2FF),
            )
            GoalInfoCard(
                modifier = Modifier.weight(1f),
                title = "완료한 목표", // TODO :
                count = goals.filter { it.isCompleted }.size,
                icon = DobeDobeIcons.Checked,
                dropShadowColor = Color(0xFF58FF92),
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        GoalListSection(
            isNotPartiallyExpanded = isNotPartiallyExpanded,
            goals = goals,
            onGoalToggled = onGoalToggled,
            onGoalClicked = onGoalClicked,
        )
    }
}

@Composable
private fun ColumnScope.GoalListSection(
    isNotPartiallyExpanded: Boolean,
    goals: List<Goal>,
    onGoalToggled: (Long) -> Unit,
    onGoalClicked: (Long) -> Unit,
) {
    AnimatedVisibility(
        visible = isNotPartiallyExpanded,
        enter = fadeIn(),
        exit = fadeOut(
            animationSpec = spring(stiffness = Spring.StiffnessHigh),
        ),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "목표 리스트", // TODO :
                style = DobeDobeTheme.typography.heading2,
                color = DobeDobeTheme.colors.black,
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (goals.isEmpty()) {
                Text(
                    text = stringResource(R.string.feature_dashboard_goal_bottom_sheet_empty_message),
                    style = DobeDobeTheme.typography.body1,
                    color = DobeDobeTheme.colors.gray500,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize(),
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
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
    }
}

@Composable
private fun GoalInfoCard(
    title: String,
    @DrawableRes icon: Int,
    dropShadowColor: Color,
    count: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .border(
                BorderStroke(
                    width = 0.3.dp,
                    color = DobeDobeTheme.colors.gray100,
                ),
                shape = RoundedCornerShape(15.dp),
            )
            .clip(RoundedCornerShape(15.dp))
            .background(DobeDobeTheme.colors.white)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                text = title,
                style = DobeDobeTheme.typography.body3,
                color = DobeDobeTheme.colors.gray600,
            )
            Text(
                text = "$count",
                style = DobeDobeTheme.typography.heading1,
                color = DobeDobeTheme.colors.gray900,
            )
        }

        Icon(
            painter = painterResource(icon),
            contentDescription = "year",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(36.dp)
                .align(alignment = Alignment.Bottom)
                .dropShadow(
                    shape = RoundedCornerShape(15.dp),
                    color = dropShadowColor,
                    blur = 40.dp,
                    offsetX = 11.dp,
                    offsetY = 17.dp,
                ),
        )
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
        isNotPartiallyExpanded = true,
        goals = goals,
        onGoalToggled = onGoalDone,
        onGoalClicked = {},
        onTapSearchBar = {},
        onAddGoalClicked = {},
    )
}
