package com.chipichipi.dobedobe.feature.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
internal fun rememberDashboardPhotoFrameState(): DashboardPhotoFrameState {
    return remember { DashboardPhotoFrameState() }
}

@Stable
class DashboardPhotoFrameState {
    private var photoId: Int? by mutableStateOf(null)

    val isExpanded: Boolean
        get() = photoId != null

    fun isExpanded(id: Int): Boolean = photoId == id

    fun toggleExpansion(id: Int) {
        photoId = if (photoId == id) null else id
    }
}
