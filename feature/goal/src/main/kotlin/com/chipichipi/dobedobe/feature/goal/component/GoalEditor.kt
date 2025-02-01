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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeBackground
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeTextField
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.feature.goal.R

@Composable
fun GoalEditor(
    title: String,
    header: String,
    onChangeTitle: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
    onDone: (() -> Unit)? = null,
    toggleContent: @Composable (() -> Unit)? = null,
) {
    val goalTitleState = rememberTextFieldState(title)

    LaunchedEffect(Unit) {
        snapshotFlow { goalTitleState.text }
            .collect {
                onChangeTitle(goalTitleState.text.toString())
            }
    }

    Column(modifier = modifier) {
        Text(
            text = header,
            modifier = Modifier.fillMaxWidth(),
            style = DobeDobeTheme.typography.heading1,
            color = DobeDobeTheme.colors.gray900,
        )
        Spacer(Modifier.height(48.dp))

        DobeDobeTextField(
            state = goalTitleState,
            hint = stringResource(R.string.feature_detail_goal_editor_hint),
            supportMessage = stringResource(R.string.feature_detail_goal_editor_support_message),
            onKeyboardAction = { onDone?.invoke() },
            errorMessage = errorMessage,
        )
        if (toggleContent != null) {
            Spacer(Modifier.weight(1f))
            toggleContent()
        }
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
                header = "어떤 목표를 이루고 싶나요?",
                onChangeTitle = {},
                toggleContent = {
                    Text(text = "Toggle Content")
                },
            )
        }
    }
}
