package com.chipichipi.dobedobe.feature.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.feature.dashboard.model.DashboardPhotoConfig

@Composable
internal fun EditModePhotoFrame(
    config: DashboardPhotoConfig,
    url: String,
    rotation: Float,
    onClick: () -> Unit,
) {
    val offsetX = config.offsetX.dp + if (config.offsetX < 0) (-4).dp else 4.dp
    val offsetY = config.offsetY.dp - 4.dp

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = config.alignment,
    ) {
        // TODO : 색상 변경 필요
        Box(
            modifier = Modifier
                .offset(
                    x = offsetX,
                    y = offsetY,
                )
                .rotate(rotation)
                .border(
                    width = 4.dp,
                    color = Color.White.copy(0.25f),
                    shape = RoundedCornerShape(24.dp),
                )
                .padding(3.dp)
                .size(config.size.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFFE5E7EB)),
        ) {
            if (url.isNotEmpty()) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(onClick = onClick),
                    contentScale = ContentScale.FillBounds,
                    model = url,
                    contentDescription = null,
                )
            } else {
                // TODO : 색상 변경 필요
                EmptyPhotoFrame(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFE5E7EB))
                        .clickable(onClick = onClick),
                )
            }
        }
    }
}

@ThemePreviews
@Composable
private fun EditModePhotoFramePreview() {
    DobeDobeTheme {
        EditModePhotoFrame(
            config = DashboardPhotoConfig.TOP,
            url = "",
            rotation = 40f,
        ) { }
    }
}
