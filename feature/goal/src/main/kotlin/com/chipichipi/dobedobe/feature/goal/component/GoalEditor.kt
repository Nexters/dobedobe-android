package com.chipichipi.dobedobe.feature.goal.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeBackground
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeTextField
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme

@Composable
fun GoalEditor(
    title: String,
    onChangeTitle: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
    toggleContent: @Composable (() -> Unit)? = null,
) {
    val goalTitleState = rememberTextFieldState(title)
    LaunchedEffect(goalTitleState.text) {
        onChangeTitle(goalTitleState.text.toString())
    }
    Column(modifier = modifier) {
        // TODO : Writing, textStyle 수정 필요
        Text(
            text = "어떤 목표를 이루고 싶나요?",
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = 33.sp,
            color = Color(0xFF262C36),
        )
        Spacer(Modifier.height(48.dp))

        DobeDobeTextField(
            state = goalTitleState,
            hint = "목표 이름",
            supportMessage = "목표가 간결할수록 집중력이 높아져요.",
            errorMessage = errorMessage,
        )
        Spacer(Modifier.height(47.dp))

        Spacer(Modifier.weight(1f))
        toggleContent?.invoke()
    }
}

@Preview
@Composable
private fun GoalDetailPreview() {
    DobeDobeTheme {
        DobeDobeBackground {
            GoalEditor(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                title = "스쿼트 50개",
                onChangeTitle = {},
            )
        }
    }
}
