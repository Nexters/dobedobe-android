package com.chipichipi.dobedobe.feature.dashboard.component

import androidx.annotation.RawRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.feature.dashboard.R
import kotlinx.coroutines.delay

@Composable
internal fun DashboardCharacter(
    @RawRes defaultRes: Int,
    @RawRes reactionRes: Int,
    onChangeBubble: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isResetTrigger by remember { mutableStateOf(false) }

    val defaultComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(defaultRes)
    )
    val reactionComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(reactionRes)
    )

    val currentComposition =
        if (isResetTrigger) reactionComposition else defaultComposition

    val progress by animateLottieCompositionAsState(
        composition = currentComposition,
        iterations = Int.MAX_VALUE
    )

    LaunchedEffect(isResetTrigger) {
        if (isResetTrigger) {
            onChangeBubble()
            delay(4000)
            isResetTrigger = false
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter,
    ) {
        LottieAnimation(
            composition = currentComposition,
            progress = { progress },
            modifier = Modifier
                .size(262.dp, 328.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) {
                    isResetTrigger = true
                },
        )
    }
}

@ThemePreviews
@Composable
private fun DashboardCharacterPreview() {
    DobeDobeTheme {
        DashboardCharacter(
            defaultRes = R.raw.rabbit01,
            reactionRes = R.raw.rabbit02,
            onChangeBubble = {},
        )
    }
}
