package com.chipichipi.dobedobe.core.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.icon.DobeDobeIcons
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme

@Composable
fun DobeDobeCheckBox(
    size: Dp,
    checked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    enabled: Boolean = true,
    @DrawableRes checkedIconRes: Int = DobeDobeIcons.Checked,
    @DrawableRes uncheckedIconRes: Int = DobeDobeIcons.Unchecked,
) {
    Box(
        modifier = modifier
            .size(size)
            .clickable(
                onClick = { onCheckedChange?.invoke(!checked) },
                enabled = enabled,
                role = Role.Checkbox,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(
                    bounded = false,
                    radius = size / 2,
                ),
            ),
        contentAlignment = Alignment.Center,
    ) {
        val iconRes = if (checked) checkedIconRes else uncheckedIconRes

        Icon(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = "checked",
            tint = Color.Unspecified,
        )
    }
}

@ThemePreviews
@Composable
private fun DobeDobeCheckBoxPreview() {
    DobeDobeTheme {
        Column {
            DobeDobeCheckBox(checked = true, enabled = true, size = 36.dp)
            DobeDobeCheckBox(checked = false, enabled = true, size = 36.dp)
        }
    }
}
