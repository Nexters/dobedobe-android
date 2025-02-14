package com.chipichipi.dobedobe.core.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeTextButton
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.core.model.CharacterType

@Composable
fun SelectCharacterScreen(
    selectedCharacter: CharacterType,
    onCharacterToggled: () -> Unit,
    onCompleted: () -> Unit,
    @StringRes buttonText: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.select_character_title),
            style = DobeDobeTheme.typography.heading1,
            color = DobeDobeTheme.colors.black,
        )
        Spacer(modifier = Modifier.height(48.dp))

        BirdCard(
            selected = selectedCharacter == CharacterType.Bird,
            onClick = onCharacterToggled,
        )
        Spacer(modifier = Modifier.height(32.dp))

        RabbitCard(
            selected = selectedCharacter == CharacterType.Rabbit,
            onClick = onCharacterToggled,
        )
        Spacer(modifier = Modifier.weight(1f))

        DobeDobeTextButton(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(56.dp),
            onClick = onCompleted,
        ) {
            Text(
                text = stringResource(buttonText),
                style = DobeDobeTheme.typography.heading2,
                color = DobeDobeTheme.colors.white,
            )
        }
    }
}

@ThemePreviews
@Composable
private fun SelectCharacterScreenPreview() {
    DobeDobeTheme {
        SelectCharacterScreen(
            selectedCharacter = CharacterType.Rabbit,
            onCharacterToggled = {},
            onCompleted = {},
            buttonText = R.string.select_character_title
        )
    }
}
