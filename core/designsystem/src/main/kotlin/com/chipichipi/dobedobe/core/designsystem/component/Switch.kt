package com.chipichipi.dobedobe.core.designsystem.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme

@Composable
fun DobeDobeSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
) {
    Switch(
        modifier = modifier,
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled,
        interactionSource = interactionSource,
        colors = SwitchDefaults.colors(
            checkedThumbColor = DobeDobeTheme.colors.white,
            checkedTrackColor = DobeDobeTheme.colors.gray900,
            checkedBorderColor = DobeDobeTheme.colors.gray900,
            uncheckedThumbColor = DobeDobeTheme.colors.gray300,
            uncheckedTrackColor = DobeDobeTheme.colors.white,
            uncheckedBorderColor = DobeDobeTheme.colors.gray300
        ),
    )
}

@ThemePreviews
@Composable
private fun DobeDobeSwitchPreview() {
    DobeDobeTheme {
        DobeDobeSwitch(
            checked = true,
            onCheckedChange = {},
        )
    }
}
