package com.chipichipi.dobedobe.feature.dashboard.model

import androidx.compose.runtime.Stable

@Stable
data class DashboardPhotoState(
    val config: DashboardPhotoConfig,
    val imageUrl: String,
)
