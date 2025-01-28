package com.chipichipi.dobedobe.feature.dashboard.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun EmptyPhotoFrame(
    modifier: Modifier = Modifier,
) {
    // TODO : 디자인 나오면 변경 필요
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.Rounded.Add,
            contentDescription = "add",
            tint = Color.Black,
        )
    }
}
