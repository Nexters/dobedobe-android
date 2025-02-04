package com.chipichipi.dobedobe.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme

@Composable
fun DobeDobeBubble(
    contentAlignment: Alignment,
    modifier: Modifier = Modifier,
    tailPositionX: Float = 0.3f,
    tailSize: DpSize = BubbleDefaults.tailSize(),
    content: @Composable BoxScope.() -> Unit,
) {
    val density = LocalDensity.current
    val bubbleShape = bubbleShape(density, tailPositionX, tailSize)
    Box(
        modifier = Modifier
            .sizeIn(
                minWidth = BubbleDefaults.MinWidth,
                minHeight = BubbleDefaults.MinHeight,
                maxWidth = BubbleDefaults.MaxWidth,
            )
            .shadow(3.dp, bubbleShape)
            .clip(bubbleShape)
            .then(modifier)
            .padding(BubbleDefaults.ContentPadding)
            .padding(bottom = tailSize.height),
        contentAlignment = contentAlignment,
        content = content,
    )
}

private fun bubbleShape(
    density: Density,
    tailPositionX: Float = 0.3f,
    tailSize: DpSize = BubbleDefaults.tailSize(),
) = GenericShape { size, _ ->
    with(density) {
        val cornerRadius = 16.dp.toPx()
        val tailWidthPx = tailSize.width.toPx()
        val tailHeightPx = tailSize.height.toPx()

        val bubbleWidth = size.width
        val bubbleHeight = size.height - tailHeightPx

        val tailCenterX = bubbleWidth * tailPositionX
        val tailStartX = tailCenterX - tailWidthPx / 2
        val tailEndX = tailCenterX + tailWidthPx / 2

        drawTopEdge(bubbleWidth, cornerRadius)
        drawRightEdge(bubbleWidth, bubbleHeight, cornerRadius)
        drawBubbleTail(bubbleHeight, tailStartX, tailEndX, tailHeightPx, density)
        drawLeftEdge(bubbleHeight, cornerRadius)
        drawBottomEdge(cornerRadius)

        close()
    }
}

private fun Path.drawTopEdge(width: Float, cornerRadius: Float) {
    moveTo(cornerRadius, 0f)
    lineTo(width - cornerRadius, 0f)
    arcTo(
        rect = Rect(
            left = width - 2 * cornerRadius,
            top = 0f,
            right = width,
            bottom = 2 * cornerRadius,
        ),
        startAngleDegrees = 270f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false,
    )
}

private fun Path.drawRightEdge(width: Float, height: Float, cornerRadius: Float) {
    lineTo(width, height - cornerRadius)
    arcTo(
        rect = Rect(
            left = width - 2 * cornerRadius,
            top = height - 2 * cornerRadius,
            right = width,
            bottom = height,
        ),
        startAngleDegrees = 0f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false,
    )
}

private fun Path.drawBubbleTail(
    height: Float,
    tailStartX: Float,
    tailEndX: Float,
    tailHeight: Float,
    density: Density,
) {
    with(density) {
        lineTo(tailEndX, height)
        // 꼬리 오른쪽 부분 그리기
        relativeCubicTo(
            dx1 = -13.dp.toPx(),
            dy1 = 10.dp.toPx(),
            dx2 = 8.dp.toPx(),
            dy2 = 24.dp.toPx(),
            dx3 = -8.dp.toPx(),
            dy3 = tailHeight,
        )

        // 꼬리 왼쪽 부분 시작점
        val x = tailEndX - 11.dp.toPx()
        val y = height + tailHeight

        // 꼬리 왼쪽 부분 그리기
        cubicTo(
            x1 = x - 14.dp.toPx(),
            y1 = y - 8.dp.toPx(),
            x2 = x - 9.dp.toPx(),
            y2 = height + 2.dp.toPx(),
            x3 = tailStartX,
            y3 = height,
        )
    }
}

private fun Path.drawLeftEdge(height: Float, cornerRadius: Float) {
    lineTo(cornerRadius, height)
    arcTo(
        rect = Rect(
            left = 0f,
            top = height - 2 * cornerRadius,
            right = 2 * cornerRadius,
            bottom = height,
        ),
        startAngleDegrees = 90f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false,
    )
}

private fun Path.drawBottomEdge(cornerRadius: Float) {
    lineTo(0f, cornerRadius)
    arcTo(
        rect = Rect(
            left = 0f,
            top = 0f,
            right = 2 * cornerRadius,
            bottom = 2 * cornerRadius,
        ),
        startAngleDegrees = 180f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false,
    )
}

object BubbleDefaults {
    val MinWidth = 135.dp
    val MinHeight = 75.dp
    val MaxWidth = 287.dp

    private val TailHeight = 20.dp
    private val TailWidth = 34.dp

    private val BubbleVerticalPadding = 16.dp
    private val BubbleHorizontalPadding = 20.dp

    val ContentPadding = PaddingValues(
        horizontal = BubbleHorizontalPadding,
        vertical = BubbleVerticalPadding,
    )

    fun tailSize(tailHeight: Dp = TailHeight, tailWidth: Dp = TailWidth) =
        DpSize(tailWidth, tailHeight)
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
        ) {
            DobeDobeBubble(
                modifier = Modifier
                    .background(Color.White)
                    .clickable { },
                tailPositionX = 0.5f,
                contentAlignment = Alignment.TopCenter,
            ) {
                Text("꿈은 이루어진다 꿈은 이루어진다 꿈은 이루어진다 꿈은 이루어진다 꿈은 이루어진다 꿈은 이루어진다 ⭐️", fontSize = 15.sp)
            }

            DobeDobeBubble(
                modifier = Modifier
                    .background(Color.White)
                    .clickable { },
                tailPositionX = 0.5f,
                contentAlignment = Alignment.TopCenter,
            ) {
                Text("꿈은 이루어진다️", fontSize = 15.sp)
            }
        }
    }
}
