package com.chipichipi.dobedobe.feature.dashboard

import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoState

@Composable
internal fun rememberDashboardPhotoFramesState(
    photoState: List<DashboardPhotoState>,
): DashboardPhotoFramesState {
    return remember {
        DashboardPhotoFramesState(
            photoState = photoState,
        )
    }
}

@Stable
class DashboardPhotoFramesState(
    photoState: List<DashboardPhotoState>,
) {
    private var currentId: Int? by mutableStateOf(null)
    private var previousId: Int? by mutableStateOf(null)
    val rotationMap = photoState.associate { state ->
        state.config.id to Animatable(state.config.rotationZ)
    }

    val isExpanded: Boolean
        get() = currentId != null

    val currentPhotoId: Int?
        get() = currentId

    val previousPhotoId: Int?
        get() = previousId

    fun isExpanded(id: Int?): Boolean {
        return currentId == id
    }

    fun toggleExpansion(id: Int) {
        previousId = currentId
        currentId = if (currentId == id) null else id
    }
}
