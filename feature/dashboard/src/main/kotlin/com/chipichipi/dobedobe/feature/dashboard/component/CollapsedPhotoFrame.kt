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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoConfig

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun SharedTransitionScope.CollapsedPhotoFrame(
    config: DashboardPhotoConfig,
    url: String,
    isExpanded: Boolean,
    rotation: Animatable<Float, AnimationVector1D>?,
    onToggleExpansion: (Int) -> Unit,
    onEmptyFrameClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CollapsedPhotoFrame(
        rotation = rotation?.value ?: 0f,
        isExpanded = isExpanded,
        config = config,
        url = url,
        onToggleExpansion = {
            onToggleExpansion(config.id)
        },
        onEmptyFrameClick = onEmptyFrameClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.CollapsedPhotoFrame(
    rotation: Float,
    isExpanded: Boolean,
    config: DashboardPhotoConfig,
    url: String,
    onToggleExpansion: () -> Unit,
    onEmptyFrameClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        modifier = modifier,
        targetState = isExpanded,
        transitionSpec = {
            fadeIn() togetherWith fadeOut()
        },
        label = "CollapsedPhotoFrame",
    ) { targetState ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            if (!targetState) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = config.alignment,
                ) {
                    if (url.isNotEmpty()) {
                        AsyncImage(
                            modifier = Modifier
                                .offset(
                                    x = config.offsetX.dp,
                                    y = config.offsetY.dp,
                                )
                                .size(config.size.dp)
                                .sharedElement(
                                    rememberSharedContentState(key = "${config.id}"),
                                    animatedVisibilityScope = this@AnimatedContent,
                                )
                                .rotate(rotation)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable(onClick = onToggleExpansion),
                            contentScale = ContentScale.FillBounds,
                            model = url,
                            contentDescription = null,
                        )
                    } else {
                        EmptyPhotoFrame(
                            modifier = Modifier
                                .offset(
                                    x = config.offsetX.dp,
                                    y = config.offsetY.dp,
                                )
                                .size(config.size.dp)
                                .rotate(rotation)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF616161))
                                .clickable(onClick = onEmptyFrameClick),
                        )
                    }
                }
            }
        }
    }
}
