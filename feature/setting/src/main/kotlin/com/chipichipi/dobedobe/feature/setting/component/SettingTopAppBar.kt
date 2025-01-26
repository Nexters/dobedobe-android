package com.chipichipi.dobedobe.feature.setting.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeTopAppBar
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingTopAppBar(
    navigateToBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DobeDobeTopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(
                modifier = Modifier.size(48.dp),
                onClick = navigateToBack,
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = null,
                )
            }
        },
    )
}

@ThemePreviews
@Composable
private fun SettingTopAppBarPreview() {
    DobeDobeTheme {
        SettingTopAppBar(
            navigateToBack = {},
        )
    }
}
