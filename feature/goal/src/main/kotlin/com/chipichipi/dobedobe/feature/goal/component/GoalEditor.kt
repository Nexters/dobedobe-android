package com.chipichipi.dobedobe.feature.goal.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeBackground
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeBubble
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeTextButton
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeTextField
import com.chipichipi.dobedobe.core.designsystem.component.TailPosition
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.feature.goal.R

@Composable
fun GoalEditor(
    titleState: TextFieldState,
    buttonText: String,
    modifier: Modifier = Modifier,
    supportMessage: String = "",
    errorMessage: String? = null,
    focusRequester: FocusRequester,
    onDone: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.rabit_avatar),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(84.dp)
                .border(1.dp, Color(0xFFFFA8E7), CircleShape)
                .padding(1.dp)
                .clip(CircleShape)
                .background(Color(0xFFFFF2FF)),
            contentScale = ContentScale.Crop,
        )

        Box(modifier = Modifier.offset(y = (-11).dp)) {
            DobeDobeBubble(
                modifier = Modifier
                    .width(220.dp)
                    .background(Color.White),
                tailPositionX = 0.43f,
                tailPosition = TailPosition.Top,
                contentAlignment = Alignment.TopCenter,
            ) {
                Text(
                    text = titleState.text.toString()
                        .ifBlank { stringResource(R.string.feature_detail_goal_editor_empty_bubble) },
                    style = DobeDobeTheme.typography.body2,
                    color = DobeDobeTheme.colors.gray700,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        DobeDobeTextField(
            state = titleState,
            hint = stringResource(R.string.feature_detail_goal_editor_hint),
            supportMessage = supportMessage,
            maxLines = 2,
            onKeyboardAction = { onDone?.invoke() },
            errorMessage = errorMessage,
            modifier = Modifier.focusRequester(focusRequester),
        )

        Spacer(modifier = Modifier.height(12.dp))
        Spacer(Modifier.weight(1f))

        DobeDobeTextButton(
            modifier = Modifier
                .fillMaxWidth()
                .requiredSizeIn(minHeight = 56.dp),
            onClick = { onDone?.invoke() },
        ) {
            Text(
                text = buttonText,
                style = DobeDobeTheme.typography.heading2,
                color = DobeDobeTheme.colors.white,
            )
        }
    }
}

@Preview
@Composable
private fun GoalDetailEmptyPreview() {
    DobeDobeTheme {
        DobeDobeBackground {
            val titleState = rememberTextFieldState("")
            GoalEditor(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 32.dp, horizontal = 24.dp)
                    .imePadding(),
                supportMessage = stringResource(R.string.feature_detail_goal_editor_support_message),
                titleState = titleState,
                focusRequester = FocusRequester(),
                buttonText = "Done",
            )
        }
    }
}

@Preview
@Composable
private fun GoalDetailPreview() {
    DobeDobeTheme {
        DobeDobeBackground {
            val titleState = rememberTextFieldState("살을 좀 빼야할 것 같다 운동 언제 가지 ㅜㅜ")
            GoalEditor(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 32.dp, horizontal = 24.dp)
                    .imePadding(),
                supportMessage = stringResource(R.string.feature_detail_goal_editor_support_message),
                titleState = titleState,
                focusRequester = FocusRequester(),
                buttonText = "Done",
            )
        }
    }
}
