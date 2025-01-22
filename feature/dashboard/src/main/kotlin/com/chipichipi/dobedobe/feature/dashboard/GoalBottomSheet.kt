package com.chipichipi.dobedobe.feature.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeCheckBox
import com.chipichipi.dobedobe.core.model.Goal
import com.chipichipi.dobedobe.feature.dashboard.preview.GoalPreviewParameterProvider

@Composable
internal fun GoalBottomSheetContent(
    goals: List<Goal>,
    onGoalDone: (Goal) -> Unit,
    onGoalClicked: (Goal) -> Unit,
) {
    Column {
        GoalBottomSheetHeader()
        Spacer(modifier = Modifier.height(15.dp))
        GoalContent(
            goals = goals,
            onGoalDone = onGoalDone,
            onGoalClicked = onGoalClicked,
        )
    }
}

@Composable
private fun GoalBottomSheetHeader() {
    // TODO: 검색 기능 추가, parameter 는 그때 추가
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            "나의 목표",
            // TODO : stringResource 추가 필요
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
        )
        IconButton(
            modifier =
                Modifier
                    .size(42.dp)
                    .offset {
                        IntOffset(x = 12.dp.roundToPx(), y = 0)
                    },
            onClick = {},
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                modifier = Modifier.size(18.dp),
                contentDescription = "목표 검색",
                // TODO : stringResource 추가 필요
            )
        }
    }
}

@Composable
private fun GoalContent(
    goals: List<Goal>,
    onGoalDone: (Goal) -> Unit,
    onGoalClicked: (Goal) -> Unit,
) {
    LazyColumn(
        modifier =
            Modifier
                .fillMaxWidth()
                .requiredHeightIn(min = 200.dp),
        // TODO: 최소 높이 조절 필요
        verticalArrangement = Arrangement.spacedBy(18.dp),
        contentPadding = PaddingValues(horizontal = 24.dp),
    ) {
        items(goals) { goal ->
            Goal(
                goal = goal,
                onGoalDone = { onGoalDone(goal) },
                onGoalClicked = { onGoalClicked(goal) },
            )
        }
    }
}

@Composable
private fun Goal(
    goal: Goal,
    onGoalDone: () -> Unit,
    onGoalClicked: () -> Unit,
) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(24.dp),
        onClick = onGoalClicked,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, top = 17.dp, bottom = 18.dp, end = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DobeDobeCheckBox(
                modifier = Modifier.size(29.dp),
                checked = goal.isDone,
                onCheckedChange = onGoalDone,
            )
            Spacer(modifier = Modifier.width(11.dp))
            Text(
                text = goal.title,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                lineHeight = 21.sp,
                style = MaterialTheme.typography.bodyLarge,
                // TODO : font 조절 필요 ,
            )
            Spacer(modifier = Modifier.weight(1f))
            if (goal.isPinned) { // TODO : 임시 값, 추후 디자인 변경 필요
                Icon(
                    imageVector = Icons.Default.Pin,
                    contentDescription = "고정된 목표",
                    // TODO : stringResource 추가 필요
                )
            }
        }
    }
}

@Preview
@Composable
private fun GoalBottomSheetContentPreview(
    @PreviewParameter(GoalPreviewParameterProvider::class) pGoals: List<Goal>,
) {
    var goals by remember { mutableStateOf(pGoals) }
    val onGoalDone: (Goal) -> Unit = {
        goals =
            goals.map { goal ->
                if (goal.id == it.id) {
                    return@map if (goal.state == Goal.State.Done) {
                        goal.copy(state = Goal.State.Todo)
                    } else {
                        goal.copy(state = Goal.State.Done)
                    }
                }
                goal
            }
    }
    GoalBottomSheetContent(
        goals = goals,
        onGoalDone = onGoalDone,
        onGoalClicked = {},
    )
}
