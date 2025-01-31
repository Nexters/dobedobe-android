package com.chipichipi.dobedobe.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme

@Composable
fun DobeDobeTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    hint: String = "",
    supportMessage: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    onKeyboardAction: KeyboardActionHandler? = null,
    imeAction: ImeAction = ImeAction.Done,
    errorMessage: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = DobeDobeTheme.typography.title1.copy(
        color = DobeDobeTheme.colors.gray900,
    ),
) {
    BasicTextField(
        state = state,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        decorator = { innerTextField ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Box {
                    if (state.text.isEmpty()) {
                        Text(
                            text = hint,
                            color = DobeDobeTheme.colors.gray200,
                            style = textStyle,
                        )
                    }
                    innerTextField()
                }

                SupportMessage(
                    supportMessage = supportMessage,
                    errorMessage = errorMessage,
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction,
        ),
        onKeyboardAction = onKeyboardAction,
        modifier = modifier,
    )
}

@Composable
private fun SupportMessage(
    supportMessage: String,
    errorMessage: String? = null,
) {
    if (errorMessage == null && supportMessage.isNotBlank()) {
        Text(
            text = supportMessage,
            style = DobeDobeTheme.typography.body3,
            color = DobeDobeTheme.colors.gray500,
        )
    }
    if (errorMessage != null) {
        Text(
            text = errorMessage,
            style = DobeDobeTheme.typography.body3,
            color = DobeDobeTheme.colors.red,
        )
    }
}

@ThemePreviews
@Composable
private fun DobeDobeTextFieldPreview() {
    DobeDobeTheme {
        val textState = rememberTextFieldState(initialText = "")
        DobeDobeTextField(
            state = textState,
            supportMessage = "목표가 간결할수록 집중력이 높아져요.",
        )
    }
}

@ThemePreviews
@Composable
private fun DobeDobeTextFieldHintPreview() {
    DobeDobeTheme {
        Surface {
            val textState = rememberTextFieldState(initialText = "")
            DobeDobeTextField(
                state = textState,
                hint = "안녕하세요 Hint입니다",
                supportMessage = "목표가 간결할수록 집중력이 높아져요.",
            )
        }
    }
}

@ThemePreviews
@Composable
private fun DobeDobeTextFieldErrorPreview() {
    DobeDobeTheme {
        Surface {
            val textState = rememberTextFieldState("네 저는 Error 입니다")

            DobeDobeTextField(
                state = textState,
                supportMessage = "목표가 간결할수록 집중력이 높아져요.",
                errorMessage = "에러 메시지",
            )
        }
    }
}
