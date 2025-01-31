package com.chipichipi.dobedobe.feature.dashboard.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeTopAppBar
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.icon.DobeDobeIcons
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.feature.dashboard.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DashboardViewModeTopAppBar(
    onEditClick: () -> Unit,
    navigateToSetting: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DobeDobeTopAppBar(
        modifier = modifier,
        actions = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = onEditClick,
                ) {
                    Icon(
                        painter = painterResource(DobeDobeIcons.Edit),
                        contentDescription = "edit icon",
                        tint = Color.Unspecified,
                    )
                }

                IconButton(
                    onClick = navigateToSetting,
                ) {
                    Icon(
                        painter = painterResource(DobeDobeIcons.Setting),
                        contentDescription = "setting icon",
                        tint = Color.Unspecified,
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DashboardEditModeTopAppBar(
    onToggleMode: () -> Unit,
    onSavePhotos: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DobeDobeTopAppBar(
        modifier = modifier.fillMaxWidth(),
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextButton(
                    onClick = onToggleMode,
                    contentPadding = PaddingValues(horizontal = 24.dp),
                ) {
                    Text(
                        modifier = Modifier
                            .clickable(onClick = onToggleMode),
                        text = stringResource(R.string.feature_dashboard_edit_mode_top_bar_cancel),
                        style = DobeDobeTheme.typography.body1,
                        color = DobeDobeTheme.colors.white,
                    )
                }

                TextButton(
                    onClick = onSavePhotos,
                    contentPadding = PaddingValues(horizontal = 24.dp),
                ) {
                    Text(
                        text = stringResource(R.string.feature_dashboard_edit_mode_top_bar_confirm),
                        style = DobeDobeTheme.typography.body1,
                        color = DobeDobeTheme.colors.green2,
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
    )
}

@ThemePreviews
@Composable
private fun DashboardViewModeTopAppBarPreview() {
    DobeDobeTheme {
        DashboardViewModeTopAppBar(
            onEditClick = {},
            navigateToSetting = {},
        )
    }
}

@ThemePreviews
@Composable
private fun DashboardEditModeTopAppBarPreview() {
    DobeDobeTheme {
        DashboardEditModeTopAppBar(
            onToggleMode = {},
            onSavePhotos = {},
        )
    }
}
