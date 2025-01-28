package com.chipichipi.dobedobe.feature.dashboard.model

import android.net.Uri
import androidx.compose.runtime.Stable

@Stable
data class DashboardPhotoState(
    val config: DashboardPhotoConfig,
    val uri: Uri,
)
