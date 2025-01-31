package com.chipichipi.dobedobe.feature.goal.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
internal fun AddGoalTopAppBar(
    navigateToBack: () -> Unit,
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
                    tint = DobeDobeTheme.colors.gray500,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DobeDobeTheme.colors.white,
        ),
    )
}

@ThemePreviews
@Composable
private fun GoalTopAppBarPreview() {
    DobeDobeTheme {
        AddGoalTopAppBar(
            navigateToBack = {},
        )
    }
}
