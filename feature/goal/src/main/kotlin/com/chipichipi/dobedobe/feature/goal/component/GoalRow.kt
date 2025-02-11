package com.chipichipi.dobedobe.feature.goal.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeCheckBox
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.icon.DobeDobeIcons
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.core.model.Goal
import kotlinx.datetime.Instant

private val PinnedIconSize: DpSize = DpSize(24.dp, 30.dp)

@Composable
fun GoalRow(
    goal: Goal,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onToggleCompleted: (() -> Unit)? = null,
) {
    Surface(
        modifier = modifier.defaultMinSize(minHeight = 58.dp),
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
                enabled = enabled,
                onCheckedChange = { onToggleCompleted?.invoke() },
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = goal.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = DobeDobeTheme.typography.heading2,
                color = DobeDobeTheme.colors.gray800,
            )
            Spacer(modifier = Modifier.width(4.dp))
            TrailingIcon(goal.isPinned)
        }
    }
}

@Composable
private fun RowScope.TrailingIcon(isPinned: Boolean) {
    if (isPinned) {
        Icon(
            imageVector = ImageVector.vectorResource(DobeDobeIcons.Bookmark),
            modifier = Modifier
                .size(PinnedIconSize)
                .align(Alignment.Top),
            contentDescription = "Favorites",
            tint = Color.Unspecified,
        )
        Spacer(modifier = Modifier.width(10.dp))
    } else {
        Spacer(modifier = Modifier.width(PinnedIconSize.width + 10.dp))
    }

    Icon(
        imageVector = ImageVector.vectorResource(DobeDobeIcons.Tap),
        modifier = Modifier.size(24.dp),
        contentDescription = "Go to detail",
        tint = Color.Unspecified,
    )
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
            GoalRow(
                goal = Goal(
                    id = 1L,
                    title = "엄청나게긴목표이름이다엄청나게긴목표다",
                    isPinned = true,
                    isCompleted = false,
                    createdAt = Instant.DISTANT_PAST,
                    completedAt = null,
                ),
                onClick = {},
                onToggleCompleted = {},
            )
            GoalRow(
                goal = Goal(
                    id = 1L,
                    title = "엄청나게긴목표이름이다엄청나게긴목표다",
                    isPinned = false,
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
