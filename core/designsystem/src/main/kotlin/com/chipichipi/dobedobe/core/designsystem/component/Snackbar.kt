package com.chipichipi.dobedobe.core.designsystem.component

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme

/**
 * TODO : Snackbar 컴포넌트 단순 Wrapper 임시 처리, 각 상태 디자인 정의 필요
 */
@Composable
fun DobeDobeSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    actionOnNewLine: Boolean = false,
    shape: Shape = SnackbarDefaults.shape,
    containerColor: Color = SnackbarDefaults.color,
    contentColor: Color = SnackbarDefaults.contentColor,
    actionColor: Color = SnackbarDefaults.actionColor,
    actionContentColor: Color = SnackbarDefaults.actionContentColor,
    dismissActionContentColor: Color = SnackbarDefaults.dismissActionContentColor,
) {
    Snackbar(
        snackbarData = snackbarData,
        modifier = modifier,
        actionOnNewLine = actionOnNewLine,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        actionColor = actionColor,
        actionContentColor = actionContentColor,
        dismissActionContentColor = dismissActionContentColor,
    )
}

@ThemePreviews
@Composable
private fun DobeDobeSnackbarPreview() {
    DobeDobeTheme {
        val snackbarData =
            object : SnackbarData {
                override val visuals: SnackbarVisuals
                    get() =
                        object : SnackbarVisuals {
                            override val message: String = "Snackbar preview"
                            override val actionLabel: String = "Retry"
                            override val withDismissAction: Boolean = true
                            override val duration: SnackbarDuration = SnackbarDuration.Short
                        }

                override fun dismiss() {}

                override fun performAction() {}
            }

        DobeDobeSnackbar(
            snackbarData = snackbarData,
        )
    }
}
