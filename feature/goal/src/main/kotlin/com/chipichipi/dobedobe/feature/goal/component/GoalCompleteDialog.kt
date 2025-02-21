package com.chipichipi.dobedobe.feature.goal.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeTextButton
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.icon.DobeDobeIcons
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.core.model.CharacterType
import com.chipichipi.dobedobe.feature.goal.R

@Composable
internal fun GoalCompleteDialog(
    visible: Boolean,
    characterType: CharacterType,
    onDismissRequest: () -> Unit,
) {
    if (!visible) return

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
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
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                DialogTitle()

                Spacer(modifier = Modifier.height(16.dp))

                DialogCharacter(characterType)

                Spacer(modifier = Modifier.height(28.dp))

                DobeDobeTextButton(
                    onClick = onDismissRequest,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(48.dp),
                ) {
                    Text(
                        text = stringResource(R.string.feature_goal_complete_dialog_button_title),
                        style = DobeDobeTheme.typography.heading2,
                    )
                }
            }
        }
    }
}

@Composable
private fun DialogTitle() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(DobeDobeIcons.Party),
            tint = Color.Unspecified,
            contentDescription = "Complete Goal",
        )

        Text(
            text = stringResource(R.string.feature_goal_complete_dialog_title),
            color = DobeDobeTheme.colors.gray400,
            style = DobeDobeTheme.typography.heading1,
        )
    }
}

@Composable
private fun DialogCharacter(
    characterType: CharacterType,
) {
    val characterRaw by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            characterType.rawRes(),
        ),
    )

    LottieAnimation(
        composition = characterRaw,
        iterations = 1,
        modifier = Modifier.size(266.dp, 332.dp),
    )
}

private fun CharacterType.rawRes(): Int = when (this) {
    CharacterType.Bird -> R.raw.goal_complete_bird
    CharacterType.Rabbit -> R.raw.goal_complete_rabbit
}

@ThemePreviews
@Composable
private fun GoalCompleteBirdDialogPreview() {
    DobeDobeTheme {
        GoalCompleteDialog(
            visible = true,
            characterType = CharacterType.Bird,
            onDismissRequest = {},
        )
    }
}

@ThemePreviews
@Composable
private fun GoalCompleteRabbitDialogPreview() {
    DobeDobeTheme {
        GoalCompleteDialog(
            visible = true,
            characterType = CharacterType.Rabbit,
            onDismissRequest = {},
        )
    }
}
