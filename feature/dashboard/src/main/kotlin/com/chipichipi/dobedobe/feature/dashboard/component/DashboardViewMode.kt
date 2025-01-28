package com.chipichipi.dobedobe.feature.dashboard.component

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.chipichipi.dobedobe.feature.dashboard.DashboardPhotoFramesState
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun SharedTransitionScope.DashboardViewMode(
    isViewMode: Boolean,
    photoState: List<DashboardPhotoState>,
    photoFramesState: DashboardPhotoFramesState,
    onToggleExpansion: (Int) -> Unit,
    onToggleMode: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        if (isViewMode) {
            DashboardCharacter(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 110.dp)
                    .zIndex(0.5f),
            )
        }

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
    }
}
