package com.chipichipi.dobedobe.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chipichipi.dobedobe.R
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeBackground
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeTextButton
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeTextField
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.feature.goal.errorMessage
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun OnboardingAddGoalRoute(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = koinViewModel(),
) {
    val titleValidResult by viewModel.titleValidResult.collectAsStateWithLifecycle()
    val errorMessage =
        titleValidResult.errorMessage()
            ?.let { stringResource(id = it) }

    OnboardingAddGoalScreen(
        errorMessage = errorMessage,
        modifier = modifier,
        completeOnboarding = viewModel::completeOnboarding,
        onChangeTitle = viewModel::changeGoalTitle,
    )
}

@Composable
private fun OnboardingAddGoalScreen(
    errorMessage: String?,
    completeOnboarding: () -> Unit,
    onChangeTitle: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.onboardingModifier(),
        horizontalAlignment = Alignment.Start,
    ) {
        val goalTitleState = rememberTextFieldState()

        LaunchedEffect(Unit) {
            snapshotFlow { goalTitleState.text }
                .collect {
                    onChangeTitle(goalTitleState.text.toString())
                }
        }

        Text(
            text = stringResource(R.string.onboarding_goal_prompt),
            style = DobeDobeTheme.typography.heading1,
            color = DobeDobeTheme.colors.gray900,
        )

        Spacer(modifier = Modifier.height(48.dp))

        DobeDobeTextField(
            state = goalTitleState,
            hint = stringResource(R.string.onboarding_goal_hint),
            supportMessage = stringResource(R.string.onboarding_goal_support_message),
            errorMessage = errorMessage,
        )

        Spacer(modifier = Modifier.weight(1f))

        DobeDobeTextButton(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(54.dp),
            onClick = completeOnboarding,
        ) {
            Text(
                text = stringResource(R.string.onboarding_goal_completed),
                style = DobeDobeTheme.typography.heading2,
                color = DobeDobeTheme.colors.white,
            )
        }
    }
}

private fun Modifier.onboardingModifier() =
    this.then(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(top = 72.dp, bottom = 32.dp)
            .navigationBarsPadding()
            .imePadding(),
    )

@Preview
@Composable
private fun OnboardingScreenPreview() {
    DobeDobeTheme {
        DobeDobeBackground {
            OnboardingAddGoalScreen(
                modifier = Modifier.fillMaxSize(),
                errorMessage = "10글자 이상 입력해주세요",
                completeOnboarding = {},
                onChangeTitle = {},
            )
        }
    }
}
