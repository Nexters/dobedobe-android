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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeCheckBox
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.core.model.Goal
import com.chipichipi.dobedobe.feature.goal.R
import kotlinx.datetime.Instant

@Composable
fun GoalRow(
    goal: Goal,
    onToggleCompleted: () -> Unit,
    onClick: () -> Unit,
) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(24.dp),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 17.dp, bottom = 18.dp, end = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DobeDobeCheckBox(
                modifier = Modifier.size(29.dp),
                checked = goal.isCompleted,
                onCheckedChange = { onToggleCompleted() },
            )
            Spacer(modifier = Modifier.width(11.dp))
            Text(
                text = goal.title,
                style = DobeDobeTheme.typography.heading2,
                color = DobeDobeTheme.colors.gray800,
            )
            Spacer(modifier = Modifier.weight(1f))
            if (goal.isPinned) {
                // TODO : 임시 Icon, 추후 디자인 변경 필요
                Icon(
                    imageVector = Icons.Default.Pin,
                    contentDescription = stringResource(R.string.feature_goal_row_icon_content_description),
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
                    isCompleted = false,
                    createdAt = Instant.DISTANT_PAST,
                    completedAt = null,
                ),
                onToggleCompleted = {},
                onClick = {},
            )
            GoalRow(
                goal = Goal(
                    id = 1L,
                    title = "Done",
                    isPinned = false,
                    isCompleted = false,
                    createdAt = Instant.DISTANT_PAST,
                    completedAt = Instant.DISTANT_PAST,
                ),
                onClick = {},
                onToggleCompleted = {},
            )
            GoalRow(
                goal = Goal(
                    id = 1L,
                    title = "Pinned",
                    isPinned = true,
                    isCompleted = false,
                    createdAt = Instant.DISTANT_PAST,
                    completedAt = Instant.DISTANT_PAST,
                ),
                onClick = {},
                onToggleCompleted = {},
            )
        }
    }
}
