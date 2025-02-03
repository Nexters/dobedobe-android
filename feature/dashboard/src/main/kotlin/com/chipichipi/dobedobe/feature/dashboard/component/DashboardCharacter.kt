package com.chipichipi.dobedobe.feature.dashboard.component

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.feature.dashboard.R
import com.chipichipi.dobedobe.feature.dashboard.util.AnimatedPngDecoder
import kotlinx.coroutines.delay

@Composable
internal fun DashboardCharacter(
    @RawRes defaultApngRes: Int,
    @RawRes reactionApngRes: Int,
    @DrawableRes placeholder: Int,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(AnimatedPngDecoder.Factory())
        }
        .build()

    var currentRaw by remember { mutableIntStateOf(defaultApngRes) }
    var isResetTrigger by remember { mutableStateOf(false) }

    LaunchedEffect(isResetTrigger) {
        if (isResetTrigger) {
            delay(4000)
            currentRaw = defaultApngRes
            isResetTrigger = false
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter,
    ) {
        AsyncImage(
            model = currentRaw,
            contentDescription = "Character Image",
            imageLoader = imageLoader,
            placeholder = painterResource(placeholder),
            modifier = Modifier
                .size(180.dp, 225.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) {
                    currentRaw = reactionApngRes
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
            defaultApngRes = R.raw.rabbit01,
            reactionApngRes = R.raw.rabbit02,
            placeholder = R.drawable.rabbit_placeholder,
        )
    }
}
