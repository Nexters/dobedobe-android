package com.chipichipi.dobedobe.feature.dashboard.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoConfig
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoState

private const val animationDuration = 500

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun SharedTransitionScope.DashboardPhotoFrameBox(
    photo: DashboardPhotoState,
    isExpanded: Boolean,
    toggleExpansion: (Int) -> Unit,
    innerPadding: PaddingValues,
) {
    val rotation = remember {
        Animatable(initialValue = photo.config.rotationZ)
    }

    val onToggleExpansion: () -> Unit = {
        if (!rotation.isRunning) {
            toggleExpansion(photo.config.id)
        }
    }

    LaunchedEffect(isExpanded) {
        rotation.animateTo(
            targetValue = if (isExpanded) 0f else photo.config.rotationZ,
            animationSpec = tween(durationMillis = animationDuration),
        )
    }

    CollapsedPhotoFrame(
        rotation = rotation.value,
        isExpanded = isExpanded,
        config = photo.config,
        imageUrl = photo.imageUrl,
        onToggleExpansion = onToggleExpansion,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .zIndex(0f),
    )

    ExpandedPhotoFrame(
        rotation = rotation.value,
        isExpanded = isExpanded,
        state = photo,
        onToggleExpansion = onToggleExpansion,
        innerPadding = innerPadding,
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f),
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.CollapsedPhotoFrame(
    rotation: Float,
    isExpanded: Boolean,
    config: DashboardPhotoConfig,
    imageUrl: String,
    onToggleExpansion: () -> Unit,
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
                    if (imageUrl.isNotEmpty()) {
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
                            model = imageUrl,
                            contentDescription = null,
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .offset(
                                    x = config.offsetX.dp,
                                    y = config.offsetY.dp,
                                )
                                .size(config.size.dp)
                                .rotate(rotation)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF616161)), // TODO: 컬러 변경 필요
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Rounded.Add, // TODO: 아이콘 변경 필요
                                contentDescription = "add",
                                tint = Color.White,
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.ExpandedPhotoFrame(
    rotation: Float,
    isExpanded: Boolean,
    state: DashboardPhotoState,
    onToggleExpansion: () -> Unit,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        modifier = modifier,
        targetState = isExpanded,
        transitionSpec = {
            fadeIn() togetherWith fadeOut()
        },
        label = "ExpandedPhotoFrame",
    ) { targetState ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            if (targetState) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f))
                        .padding(innerPadding)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) {
                            onToggleExpansion()
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(300.dp)
                            .sharedElement(
                                rememberSharedContentState(key = "${state.config.id}"),
                                animatedVisibilityScope = this@AnimatedContent,
                            )
                            .rotate(rotation)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.FillBounds,
                        model = state.imageUrl,
                        contentDescription = "expanded photo",
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@ThemePreviews
@Composable
private fun DashboardPhotoFrameBoxPreview() {
    DobeDobeTheme {
        SharedTransitionLayout {
            DashboardPhotoFrameBox(
                photo = DashboardPhotoState(
                    config = DashboardPhotoConfig.TOP,
                    imageUrl = ""
                ),
                innerPadding = PaddingValues(10.dp),
                isExpanded = false,
                toggleExpansion = {}
            )
        }
    }
}
