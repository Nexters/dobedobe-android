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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
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
                    // TODO : font 적용
                    textStyle = TextStyle(fontSize = 15.sp),
                    // TODO: ColorScheme 적용
                    modifier = Modifier.background(Color.White),
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
