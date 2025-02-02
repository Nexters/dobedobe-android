package com.chipichipi.dobedobe.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chipichipi.dobedobe.R
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeTextButton
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.core.model.CharacterType
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun OnboardingSelectCharacterRoute(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = koinViewModel(),
) {
    val selectedCharacter by viewModel.selectedCharacter.collectAsStateWithLifecycle()

    OnboardingSelectCharacterScreen(
        modifier = modifier,
        selectedCharacter = selectedCharacter,
        onCharacterToggled = viewModel::toggleCharacter,
        onOnboardingCompleted = viewModel::completeOnboarding
    )
}

@Composable
private fun OnboardingSelectCharacterScreen(
    selectedCharacter: CharacterType,
    onCharacterToggled: () -> Unit,
    onOnboardingCompleted: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(
                top = 48.dp,
                bottom = 32.dp
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.onboarding_select_character_title),
            style = DobeDobeTheme.typography.heading1,
            color = DobeDobeTheme.colors.black
        )
        Spacer(modifier = Modifier.height(48.dp))

        OnboardingBirdInfo(
            selectedCharacter = selectedCharacter,
            onClick = onCharacterToggled
        )
        Spacer(modifier = Modifier.height(32.dp))

        OnboardingRabbitInfo(
            selectedCharacter = selectedCharacter,
            onClick = onCharacterToggled
        )
        Spacer(modifier = Modifier.weight(1f))

        DobeDobeTextButton(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(56.dp),
            onClick = onOnboardingCompleted,
        ) {
            Text(
                text = stringResource(R.string.onboarding_all_complete),
                style = DobeDobeTheme.typography.heading2,
                color = DobeDobeTheme.colors.white,
            )
        }
    }
}
