package com.chipichipi.dobedobe.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun OnboardingSelectCharacterRoute(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = koinViewModel(),
) {
    OnboardingSelectCharacterScreen(modifier = modifier)
}

@Composable
private fun OnboardingSelectCharacterScreen(
    modifier: Modifier = Modifier
) {

}