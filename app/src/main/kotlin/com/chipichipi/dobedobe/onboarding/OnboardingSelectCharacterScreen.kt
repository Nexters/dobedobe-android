package com.chipichipi.dobedobe.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chipichipi.dobedobe.core.ui.SelectCharacterScreen
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun OnboardingSelectCharacterRoute(
    modifier: Modifier = Modifier,
    viewModel: OnboardingSelectCharacterViewModel = koinViewModel(),
) {
    val selectedCharacter by viewModel.selectedCharacter.collectAsStateWithLifecycle()

    SelectCharacterScreen(
        modifier = modifier,
        selectedCharacter = selectedCharacter,
        onCharacterToggled = viewModel::toggleCharacter,
        onCompleted = viewModel::completeOnboarding,
    )
}
