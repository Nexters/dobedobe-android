package com.chipichipi.dobedobe.feature.setting.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.core.model.CharacterType
import com.chipichipi.dobedobe.core.ui.SelectCharacterScreen

@Composable
internal fun SelectCharacterDialog(
    onDismissRequest: () -> Unit,
    characterType: CharacterType,
    onCharacterChangeCompleted: () -> Unit,
    onCharacterToggled: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(16.dp),
            color = DobeDobeTheme.colors.white,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 32.dp),
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    IconButton(
                        onClick = onDismissRequest,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Cancel,
                            contentDescription = null
                        )
                    }
                }

                SelectCharacterScreen(
                    selectedCharacter = characterType,
                    onCompleted = {
                        onCharacterChangeCompleted()
                        onDismissRequest()
                    },
                    onCharacterToggled = onCharacterToggled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                )
            }
        }
    }
}

@ThemePreviews
@Composable
private fun SelectCharacterDialogPreview() {
    DobeDobeTheme {
        SelectCharacterDialog(
            onDismissRequest = {},
            characterType = CharacterType.Rabbit,
            onCharacterChangeCompleted = {},
            onCharacterToggled = {}
        )
    }
}
