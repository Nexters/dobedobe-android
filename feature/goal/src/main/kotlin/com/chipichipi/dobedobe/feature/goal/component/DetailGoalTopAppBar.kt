package com.chipichipi.dobedobe.feature.goal.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeTopAppBar
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.icon.DobeDobeIcons
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.feature.goal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DetailGoalTopAppBar(
    navigateToBack: () -> Unit,
    onRemoveGoal: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DobeDobeTopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = navigateToBack) {
                Icon(
                    imageVector = ImageVector.vectorResource(DobeDobeIcons.ArrowBack),
                    modifier = Modifier.size(24.dp),
                    contentDescription = stringResource(R.string.feature_goal_navigate_back_icon_content_description),
                    tint = Color.Unspecified,
                )
            }
        },
        actions = {
            TextButton(
                onClick = onRemoveGoal,
                colors = ButtonDefaults.textButtonColors().copy(
                    contentColor = DobeDobeTheme.colors.red,
                ),
            ) {
                Text(
                    text = stringResource(R.string.feature_detail_goal_top_bar_remove),
                    style = DobeDobeTheme.typography.body1,
                )
            }
        },
    )
}

@ThemePreviews
@Composable
private fun GoalTopAppBarPreview() {
    DobeDobeTheme {
        DetailGoalTopAppBar(
            navigateToBack = {},
            onRemoveGoal = {},
        )
    }
}
