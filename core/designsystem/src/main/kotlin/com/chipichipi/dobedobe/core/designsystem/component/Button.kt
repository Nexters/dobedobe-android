package com.chipichipi.dobedobe.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme

/**
 * TODO : Button 컴포넌트 단순 Wrapper 임시 처리, 각 상태 디자인 정의 필요
 */
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
        colors = ButtonDefaults.textButtonColors(),
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
        border =
            BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
            ),
        contentPadding = ButtonDefaults.ContentPadding,
        content = content,
    )
}

@ThemePreviews
@Composable
fun DobeDobeTextButtonPreview() {
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
fun DobeDobeOutlinedButtonPreview() {
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
