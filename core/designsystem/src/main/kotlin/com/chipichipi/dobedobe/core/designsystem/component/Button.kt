package com.chipichipi.dobedobe.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme

@Composable
fun DobeDobeTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            contentColor = DobeDobeTheme.colors.white,
            containerColor = DobeDobeTheme.colors.gray900,
        ),
        shape = RoundedCornerShape(12.dp),
        contentPadding = ButtonDefaults.TextButtonContentPadding,
        content = content,
    )
}

@Composable
fun DobeDobeOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline,
        ),
        contentPadding = ButtonDefaults.ContentPadding,
        content = content,
    )
}

@ThemePreviews
@Composable
private fun DobeDobeTextButtonPreview() {
    DobeDobeTheme {
        DobeDobeBackground(
            modifier = Modifier.size(120.dp, 50.dp),
        ) {
            DobeDobeTextButton(
                onClick = {},
            ) {
                Text("text")
            }
        }
    }
}

@ThemePreviews
@Composable
private fun DobeDobeOutlinedButtonPreview() {
    DobeDobeTheme {
        DobeDobeBackground(
            modifier = Modifier.size(120.dp, 50.dp),
        ) {
            DobeDobeOutlinedButton(
                onClick = {},
            ) {
                Text("text")
            }
        }
    }
}
