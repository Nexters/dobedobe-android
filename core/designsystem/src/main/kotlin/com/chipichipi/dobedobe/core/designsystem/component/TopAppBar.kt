package com.chipichipi.dobedobe.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichpi.dobedobe.core.designsystem.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DobeDobeTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = DobeDobeTheme.colors.gray50,
    ),
) {
    TopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = navigationIcon,
        expandedHeight = 60.dp,
        actions = actions,
        windowInsets = windowInsets,
        colors = colors,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DobeDobeCenterAlignedTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = DobeDobeTheme.colors.gray50,
    ),
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = navigationIcon,
        expandedHeight = 60.dp,
        actions = actions,
        windowInsets = windowInsets,
        colors = colors,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@ThemePreviews
@Composable
private fun DobeDobeTopAppBarPreview() {
    DobeDobeTheme {
        DobeDobeTopAppBar(
            navigationIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back_24),
                    tint = Color.Unspecified,
                    contentDescription = null,
                )
            },
            actions = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_edit_24),
                        tint = Color.Unspecified,
                        contentDescription = null,
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_setting_24),
                        tint = Color.Unspecified,
                        contentDescription = null,
                    )
                }
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@ThemePreviews
@Composable
private fun DobeDobeCenterAlignedTopAppBarPreview() {
    DobeDobeTheme {
        DobeDobeCenterAlignedTopAppBar(
            navigationIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back_24),
                    tint = Color.Unspecified,
                    contentDescription = null,
                )
            },
            title = {
                Text(
                    text = "Title",
                    color = DobeDobeTheme.colors.gray700,
                    style = DobeDobeTheme.typography.body1,
                )
            },
            actions = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_edit_24),
                        tint = Color.Unspecified,
                        contentDescription = null,
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_setting_24),
                        tint = Color.Unspecified,
                        contentDescription = null,
                    )
                }
            },
        )
    }
}
