package com.chipichipi.dobedobe.feature.setting.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme

@Composable
internal fun SettingRow(
    label: String,
    modifier: Modifier = Modifier,
    trailingContent: @Composable () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(52.dp)
                .padding(
                    start = 24.dp,
                    end = 8.dp,
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = label,
                style = DobeDobeTheme.typography.body1,
                color = DobeDobeTheme.colors.black
            )

            trailingContent()
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = DobeDobeTheme.colors.gray200
        )
    }
}

@ThemePreviews
@Composable
private fun SettingRowPreview() {
    DobeDobeTheme {
        SettingRow(
            label = "TEST",
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "TEST")
            }
        }
    }
}
