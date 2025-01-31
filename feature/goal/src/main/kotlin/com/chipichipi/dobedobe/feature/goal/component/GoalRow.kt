package com.chipichipi.dobedobe.feature.goal.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeCheckBox
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.icon.DobeDobeIcons
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.core.model.Goal
import kotlinx.datetime.Instant

@Composable
fun GoalRow(
    goal: Goal,
    onToggleCompleted: () -> Unit,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.defaultMinSize(minHeight = 58.dp),
        color = DobeDobeTheme.colors.gray50,
        shape = RoundedCornerShape(24.dp),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp, end = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DobeDobeCheckBox(
                checked = goal.isCompleted,
                onCheckedChange = { onToggleCompleted() },
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = goal.title,
                style = DobeDobeTheme.typography.heading2,
                color = DobeDobeTheme.colors.gray800,
            )
            Spacer(modifier = Modifier.weight(1f))
            if (goal.isPinned) {
                Icon(
                    imageVector = ImageVector.vectorResource(DobeDobeIcons.Bookmark),
                    modifier = Modifier
                        .size(24.dp, 30.dp)
                        .align(Alignment.Top)
                        .offset(x = (-10).dp),
                    contentDescription = "Favorites",
                    tint = Color.Unspecified,
                )
            }
            Icon(
                imageVector = ImageVector.vectorResource(DobeDobeIcons.Tap),
                modifier = Modifier.size(24.dp),
                contentDescription = "Go to detail",
                tint = Color.Unspecified,
            )
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
                    isCompleted = true,
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
                    completedAt = null,
                ),
                onClick = {},
                onToggleCompleted = {},
            )
        }
    }
}
