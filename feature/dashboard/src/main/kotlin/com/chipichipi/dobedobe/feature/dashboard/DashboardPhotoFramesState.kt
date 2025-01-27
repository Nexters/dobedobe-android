package com.chipichipi.dobedobe.feature.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
internal fun rememberDashboardPhotoFramesState(): DashboardPhotoFramesState {
    return remember { DashboardPhotoFramesState() }
}

@Stable
class DashboardPhotoFramesState {
    private var currentId: Int? by mutableStateOf(null)
    private var previousId: Int? by mutableStateOf(null)

    val isExpanded: Boolean
        get() = currentId != null

    val currentPhoto: Int?
        get() = currentId

    val previousPhoto: Int?
        get() = previousId

    fun isExpanded(id: Int?): Boolean {
        return currentId == id
    }

    fun toggleExpansion(id: Int) {
        previousId = currentId
        currentId = if (currentId == id) null else id
    }
}
