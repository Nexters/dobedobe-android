package com.chipichipi.dobedobe.onboarding

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chipichipi.dobedobe.R
import com.chipichipi.dobedobe.core.ui.SelectCharacterScreen
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun OnboardingSelectCharacterRoute(
    modifier: Modifier = Modifier,
    viewModel: OnboardingSelectCharacterViewModel = koinViewModel(),
) {
    val selectedCharacter by viewModel.selectedCharacter.collectAsStateWithLifecycle()

    SelectCharacterScreen(
        selectedCharacter = selectedCharacter,
        onCharacterToggled = viewModel::toggleCharacter,
        onCompleted = viewModel::completeOnboarding,
        buttonText = R.string.onboarding_all_completed,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(
                top = 48.dp,
                bottom = 32.dp,
            ),
    )
}
