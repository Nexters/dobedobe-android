package com.chipichipi.dobedobe.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(
                top = 48.dp,
                bottom = 32.dp,
            ),
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
                text = stringResource(R.string.all_complete),
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
        )
    }
}
