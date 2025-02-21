package com.chipichipi.dobedobe.feature.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeBubble
import com.chipichipi.dobedobe.core.designsystem.component.ThemePreviews
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme

@Composable
internal fun DashboardBubble(
    title: String,
    textStyle: TextStyle,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DobeDobeBubble(
        modifier = modifier.clickable(onClick = onClick),
        contentAlignment = Alignment.TopCenter,
    ) {
        Text(
            text = title,
            style = textStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.widthIn(max = 240.dp),
        )
    }
}

@ThemePreviews
@Composable
private fun PreviewBubbleWithTail() {
    DobeDobeTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            DashboardBubble(
                modifier = Modifier.background(Color.White),
                textStyle = TextStyle(fontSize = 15.sp),
                // TODO : 랜덤 TODO 목표 받아와서 넣기,
                onClick = {},
                title = "놀고",
            )
            DashboardBubble(
                modifier = Modifier.background(Color.White),
                // TODO : 랜덤 TODO 목표 받아와서 넣기,
                textStyle = TextStyle(fontSize = 15.sp),
                onClick = {},
                title = "놀고먹고자고놀고먹고자",
            )
            DashboardBubble(
                modifier = Modifier.background(Color.White),
                textStyle = TextStyle(fontSize = 15.sp),
                // TODO : 랜덤 TODO 목표 받아와서 넣기,
                onClick = {},
                title = "놀고먹고자고놀고먹고자고힘내자",
            )
        }
    }
}
