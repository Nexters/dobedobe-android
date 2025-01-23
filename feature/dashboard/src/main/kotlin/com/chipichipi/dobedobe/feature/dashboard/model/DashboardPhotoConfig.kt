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
        rotationZ = 15f,
        alignment = Alignment.TopStart,
        offsetY = 25f,
        offsetX = 0f,
        size = 150
    ),
    MIDDLE(
        id = 2,
        rotationZ = -15f,
        alignment = Alignment.TopEnd,
        offsetY = 180f,
        offsetX = 0f,
        size = 140
    ),
    BOTTOM(
        id = 3,
        rotationZ = 10f,
        alignment = Alignment.TopStart,
        offsetY = 350f,
        offsetX = -15f,
        size = 100
    );
}
