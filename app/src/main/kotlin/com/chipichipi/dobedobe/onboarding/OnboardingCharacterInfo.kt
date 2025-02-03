package com.chipichipi.dobedobe.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.R
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.core.model.CharacterType

@Composable
internal fun OnboardingBirdInfo(
    selectedCharacter: CharacterType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val selected = selectedCharacter == CharacterType.Bird

    CharacterInfo(
        modifier = modifier,
        title = stringResource(R.string.onboarding_bird_character_description),
        characterAlignment = Alignment.BottomStart,
        bubbleAlignment = Alignment.TopEnd,
        characterRes = R.drawable.onboarding_bird,
        bubbleRes = R.drawable.bubble_bird,
        selected = selected,
        onClick = onClick,
    )
}

@Composable
internal fun OnboardingRabbitInfo(
    selectedCharacter: CharacterType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val selected = selectedCharacter == CharacterType.Rabbit

    CharacterInfo(
        modifier = modifier,
        title = stringResource(R.string.onboarding_rabbit_character_description),
        characterAlignment = Alignment.BottomEnd,
        bubbleAlignment = Alignment.TopStart,
        characterRes = R.drawable.onboarding_rabbit,
        bubbleRes = R.drawable.bubble_rabbit,
        selected = selected,
        onClick = onClick,
    )
}

@Composable
private fun CharacterInfo(
    title: String,
    characterAlignment: Alignment,
    bubbleAlignment: Alignment,
    @DrawableRes characterRes: Int,
    @DrawableRes bubbleRes: Int,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val color = if (selected) DobeDobeTheme.colors.green1 else DobeDobeTheme.colors.gray50
    val borderStroke = BorderStroke(
        width = 2.dp,
        color = if (selected) DobeDobeTheme.colors.green3 else DobeDobeTheme.colors.gray200,
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = title,
            style = DobeDobeTheme.typography.heading2,
            color = DobeDobeTheme.colors.gray900,
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(136.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable(onClick = onClick),
            shape = RoundedCornerShape(12.dp),
            border = borderStroke,
            color = color,
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    modifier = Modifier
                        .align(characterAlignment)
                        .size(192.dp, 136.dp),
                    painter = painterResource(characterRes),
                    contentDescription = "character",
                )

                Image(
                    modifier = Modifier
                        .padding(22.dp)
                        .align(bubbleAlignment)
                        .size(120.dp, 46.dp),
                    painter = painterResource(bubbleRes),
                    contentDescription = "bubble",
                )
            }

            if (!selected) {
                CharacterUnselectedOverlay()
            }
        }
    }
}

@Composable
private fun CharacterUnselectedOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = DobeDobeTheme.colors.gray50.copy(0.7f),
            ),
    )
}
