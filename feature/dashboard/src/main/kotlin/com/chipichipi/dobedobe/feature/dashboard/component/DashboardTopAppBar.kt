package com.chipichipi.dobedobe.feature.dashboard.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.AltRoute
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeTopAppBar
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DashboardTopAppBar(
    onEditClick: () -> Unit,
    onSettingClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DobeDobeTopAppBar(
        modifier = modifier,
        actions = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // TODO: 아이콘 교체 필요
                IconButton(
                    onClick = onEditClick,
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.HelpOutline,
                        contentDescription = "edit icon",
                    )
                }
                // TODO: 아이콘 교체 필요
                IconButton(
                    onClick = onSettingClick,
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.AltRoute,
                        contentDescription = "setting icon",
                    )
                }
            }
        },
        // TODO : 컬러 변경 필요
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFECFFE1),
        ),
    )
}

@ThemePreviews
@Composable
private fun DashboardTopAppBarPreview() {
    DobeDobeTheme {
        DashboardTopAppBar(
            onEditClick = {},
            onSettingClick = {},
        )
    }
}
