package com.chipichipi.dobedobe.feature.dashboard.model

import androidx.compose.ui.Alignment

enum class DashboardPhotoConfig(
    val id: Int,
    val rotationZ: Float,
    val alignment: Alignment,
    val offsetY: Float,
    val offsetX: Float,
    val size: Int,
) {
    TOP(
        id = 1,
        rotationZ = 8f,
        alignment = Alignment.TopStart,
        offsetY = 9f,
        offsetX = -10f,
        size = 140,
    ),
    MIDDLE(
        id = 2,
        rotationZ = -15f,
        alignment = Alignment.TopEnd,
        offsetY = 115f,
        offsetX = 15f,
        size = 150,
    ),
    BOTTOM(
        id = 3,
        rotationZ = -3f,
        alignment = Alignment.TopStart,
        offsetY = 340f,
        offsetX = -9f,
        size = 120,
    ),
}
