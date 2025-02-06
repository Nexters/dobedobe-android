package com.chipichipi.dobedobe.feature.dashboard

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeTextField
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
            // TODO: 함수 분리하기
            AnimatedVisibility(
                visible = isExpanded,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
            ) {
                val borderStrokeColor = DobeDobeTheme.colors.gray200
                GoalSearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .drawBehind {
                            drawLine(
                                color = borderStrokeColor,
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                strokeWidth = 1.dp.toPx(),
                            )
                        }
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                ) {
                    DobeDobeTextField(
                        modifier = Modifier.fillMaxWidth(),
                        state = rememberTextFieldState(),
                        hint = "목표 검색",
                        textStyle = DobeDobeTheme.typography.body1,
                        imeAction = ImeAction.Search,
                    )
                }
                // TODO: 요런 형식으로 바꾸기
//            GoalSearchBar(
//                queryState = queryState,
//                onQueryChanged = onSearchQueryChanged,
//                onCloseSearch = onCloseSearch,
//            )
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
            verticalArrangement = Arrangement.spacedBy(18.dp),
            contentPadding = PaddingValues(
                horizontal = 24.dp, vertical = 15.dp,
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
        onAddGoalClicked = {},
    )
}
