package com.chipichipi.dobedobe.feature.goal.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeCenterAlignedTopAppBar
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.icon.DobeDobeIcons
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.feature.goal.R

// Material TopAppBar default Padding
private val TopAppBarHorizontalPadding = 4.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GoalTopAppBar(
    title: String,
    navigateToBack: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
) {
    DobeDobeCenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                color = DobeDobeTheme.colors.gray700,
                style = DobeDobeTheme.typography.body1,
            )
        },
        navigationIcon = {
            IconButton(
                onClick = navigateToBack,
                modifier = Modifier
                    .padding(start = 12.dp - TopAppBarHorizontalPadding)
                    .size(48.dp),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(DobeDobeIcons.ArrowBack),
                    modifier = Modifier.size(24.dp),
                    contentDescription = stringResource(R.string.feature_goal_navigate_back_icon_content_description),
                    tint = DobeDobeTheme.colors.gray500,
                )
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DobeDobeTheme.colors.white,
        ),
    )
}

@ThemePreviews
@Composable
private fun GoalTopAppBarPreview() {
    DobeDobeTheme {
        Column {
            GoalTopAppBar(
                title = "Example",
                navigateToBack = {},
                actions = {
                    TextButton(
                        onClick = {},
                        colors = ButtonDefaults.textButtonColors().copy(
                            contentColor = DobeDobeTheme.colors.red,
                        ),
                        contentPadding = PaddingValues(horizontal = 20.dp),
                    ) {
                        Text(
                            text = "삭제",
                            style = DobeDobeTheme.typography.body1,
                        )
                    }
                },
            )
        }
    }
}
