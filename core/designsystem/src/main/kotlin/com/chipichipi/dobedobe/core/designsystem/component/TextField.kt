package com.chipichipi.dobedobe.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme

/**
 * TODO : TextField 컴포넌트 단순 Wrapper 임시 처리, 각 상태 디자인 정의 필요
 */
@Composable
fun DobeDobeTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    hint: String = "",
    supportMessage: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    errorMessage: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
) {
    BasicTextField(
        state = state,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = TextStyle(
            fontSize = 28.sp,
            lineHeight = 42.sp,
            fontWeight = FontWeight.SemiBold,
        ),
        decorator = { innerTextField ->
            Column(
                modifier =
                Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Box {
                    if (state.text.isEmpty()) {
                        Text(
                            text = hint,
                            color = Color(0xFFE5E7EB),
                            // TODO : Color Scheme 적용 필요
                            fontSize = 28.sp,
                            lineHeight = 42.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                    innerTextField()
                }
                Spacer(Modifier.height(8.dp))
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
        modifier = modifier,
    )
}

@Composable
private fun SupportMessage(
    supportMessage: String,
    errorMessage: String? = null,
) {
    // TODO : colorScheme 적용 필요
    if (errorMessage == null) {
        Text(
            text = supportMessage,
            fontSize = 14.sp,
            lineHeight = 21.sp,
            color = Color(0xFF7A828C),
        )
    } else {
        Text(
            text = errorMessage,
            fontSize = 14.sp,
            lineHeight = 21.sp,
            color = Color(0xFFFF354D),
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
