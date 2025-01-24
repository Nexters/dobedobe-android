package com.chipichipi.dobedobe.feature.goal.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeCheckBox
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.core.model.Goal
import kotlinx.datetime.Instant

@Composable
fun GoalRow(
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 17.dp, bottom = 18.dp, end = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DobeDobeCheckBox(
                modifier = Modifier.size(29.dp),
                checked = goal.isDone,
                onCheckedChange = { onGoalDone() },
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

@ThemePreviews
@Composable
private fun GoalRowPreview() {
    DobeDobeTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            GoalRow(
                goal = Goal(
                        id = 1L,
                        title = "Todo",
                        isPinned = false,
                        state = Goal.State.Todo,
                        createdAt = Instant.DISTANT_PAST,
                        completedAt = null,
                    ),
                onGoalDone = {},
                onGoalClicked = {},
            )
            GoalRow(
                goal = Goal(
                        id = 1L,
                        title = "Done",
                        isPinned = false,
                        state = Goal.State.Done,
                        createdAt = Instant.DISTANT_PAST,
                        completedAt = Instant.DISTANT_PAST,
                    ),
                onGoalDone = {},
                onGoalClicked = {},
            )
            GoalRow(
                goal = Goal(
                        id = 1L,
                        title = "Pinned",
                        isPinned = true,
                        state = Goal.State.Done,
                        createdAt = Instant.DISTANT_PAST,
                        completedAt = Instant.DISTANT_PAST,
                    ),
                onGoalDone = {},
                onGoalClicked = {},
            )
        }
    }
}
