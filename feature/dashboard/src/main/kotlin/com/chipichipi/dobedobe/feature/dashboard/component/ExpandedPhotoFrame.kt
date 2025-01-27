package com.chipichipi.dobedobe.feature.dashboard.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun SharedTransitionScope.ExpandedPhotoFrame(
    photoState: DashboardPhotoState?,
    rotation: Animatable<Float, AnimationVector1D>?,
    onToggleExpansion: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    ExpandedPhotoFrame(
        rotation = rotation?.value ?: 0f,
        state = photoState,
        onToggleExpansion = onToggleExpansion,
        modifier = modifier,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.ExpandedPhotoFrame(
    rotation: Float,
    state: DashboardPhotoState?,
    onToggleExpansion: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        modifier = modifier,
        targetState = state,
        transitionSpec = {
            fadeIn() togetherWith fadeOut()
        },
        label = "ExpandedPhotoFrame",
    ) { targetState ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            if (targetState != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) {
                            onToggleExpansion(targetState.config.id)
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(300.dp)
                            .sharedElement(
                                rememberSharedContentState(key = "${targetState.config.id}"),
                                animatedVisibilityScope = this@AnimatedContent,
                            )
                            .rotate(rotation)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.FillBounds,
                        model = targetState.url,
                        contentDescription = "expanded photo",
                    )
                }
            }
        }
    }
}
