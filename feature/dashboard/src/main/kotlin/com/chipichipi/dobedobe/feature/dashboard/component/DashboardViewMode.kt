package com.chipichipi.dobedobe.feature.dashboard.component

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.feature.dashboard.DashboardPhotoFramesState
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun SharedTransitionScope.DashboardViewMode(
    isViewMode: Boolean,
    photoState: List<DashboardPhotoState>,
    bubbleTitle: String,
    photoFramesState: DashboardPhotoFramesState,
    onChangeBubble: () -> Unit,
    onToggleExpansion: (Int) -> Unit,
    onToggleMode: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        photoState.forEach { photo ->
            CollapsedPhotoFrame(
                config = photo.config,
                uri = photo.uri,
                isExpanded = photoFramesState.isExpanded(photo.config.id),
                rotation = photoFramesState.rotationMap[photo.config.id],
                onToggleExpansion = onToggleExpansion,
                onEmptyFrameClick = onToggleMode,
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(0f),
            )
        }

        if (isViewMode) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(Modifier.height(13.dp))
                DashboardBubble(
                    title = bubbleTitle,
                    textStyle = DobeDobeTheme.typography.body2,
                    modifier = Modifier
                        .background(
                            color = DobeDobeTheme.colors.white,
                        ),
                    onClick = onChangeBubble,
                )
                DashboardCharacter(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(0.5f),
                )
            }
        }
    }
}
