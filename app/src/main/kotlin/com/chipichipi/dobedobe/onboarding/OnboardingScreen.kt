package com.chipichipi.dobedobe.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeTextButton
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeTextField
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun OnboardingRoute(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = koinViewModel(),
) {
    OnboardingScreen(
        modifier = modifier,
        completeOnboarding = viewModel::completeOnboarding,
    )
}

@Composable
private fun OnboardingScreen(
    completeOnboarding: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.onboardingModifier(),
        horizontalAlignment = Alignment.Start,
    ) {
        val textState = rememberTextFieldState()

        // TODO : Writing 수정 필요
        Text(
            text = "어떤 목표를 이루고 싶나요?",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        )

        Spacer(modifier = Modifier.height(32.dp))

        // TODO : Writing 수정 필요
        Text(
            text = "목표 이름",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black.copy(0.6f),
        )

        Spacer(modifier = Modifier.height(10.dp))

        DobeDobeTextField(
            state = textState,
        )

        Spacer(modifier = Modifier.weight(1f))

        // TODO : 클릭 시 Goal DB에 저장 필요
        DobeDobeTextButton(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(54.dp),
            onClick = completeOnboarding,
        ) {
            // TODO : Writing 수정 필요
            Text(
                "목표 저장하기",
            )
        }
    }
}

private fun Modifier.onboardingModifier() =
    this.then(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(top = 120.dp, bottom = 42.dp)
            .imePadding(),
    )
