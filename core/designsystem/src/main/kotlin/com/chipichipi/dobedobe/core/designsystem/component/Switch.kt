package com.chipichipi.dobedobe.core.designsystem.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme

/**
 * TODO : Switch 컴포넌트 단순 Wrapper 임시 처리, 각 상태 디자인 정의 필요
 */
@Composable
fun DobeDobeSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
) {
    // TODO : 디자인시스템 나오면 컬러 정의 필요
    Switch(
        modifier = modifier,
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled,
        interactionSource = interactionSource,
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = Color.Red,
            uncheckedThumbColor = Color.Gray,
            uncheckedTrackColor = Color.Transparent,
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
