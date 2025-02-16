package com.chipichipi.dobedobe.core.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import com.chipichipi.dobedobe.core.designsystem.icon.DobeDobeIcons
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme

@Composable
fun DobeDobeCheckBox(
    checked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    enabled: Boolean = true,
    @DrawableRes checkedIconRes: Int = DobeDobeIcons.Checked,
    @DrawableRes uncheckedIconRes: Int = DobeDobeIcons.Unchecked,
) {
    IconButton(
        onClick = { onCheckedChange?.invoke(!checked) },
        modifier = modifier.semantics {
            role = Role.Checkbox
        },
        enabled = enabled,
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
            DobeDobeCheckBox(checked = true, enabled = true)
            DobeDobeCheckBox(checked = false, enabled = true)
        }
    }
}
