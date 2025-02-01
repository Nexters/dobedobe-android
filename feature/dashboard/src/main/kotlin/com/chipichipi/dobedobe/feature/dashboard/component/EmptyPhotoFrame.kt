package com.chipichipi.dobedobe.feature.dashboard.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.icon.DobeDobeIcons
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme

@Composable
internal fun EmptyPhotoFrame(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.size(58.dp),
            painter = painterResource(DobeDobeIcons.EditPlaceholder),
            contentDescription = "edit",
            tint = Color.Unspecified,
        )
    }
}

@ThemePreviews
@Composable
private fun EmptyPhotoFramePreview() {
    DobeDobeTheme {
        EmptyPhotoFrame()
    }
}
