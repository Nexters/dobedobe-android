package com.chipichipi.dobedobe.core.designsystem.util

import android.graphics.BlurMaskFilter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.dropShadow(
    shape: Shape,
    color: Color,
    blur: Dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp,
) = this.drawWithCache {
    val spreadPx = spread.toPx()
    val blurPx = blur.toPx()

    val shadowSize = Size(
        width = size.width + spreadPx,
        height = size.height + spreadPx,
    )
    val shadowOutline = shape.createOutline(
        size = shadowSize,
        layoutDirection = layoutDirection,
        density = this,
    )

    val paint = Paint().apply {
        this.color = color
    }

    if (blurPx > 0) {
        paint.asFrameworkPaint().apply {
            maskFilter = BlurMaskFilter(blurPx, BlurMaskFilter.Blur.NORMAL)
        }
    }

    onDrawBehind {
        drawIntoCanvas { canvas ->
            canvas.save()
            (spreadPx / 2f).let {
                canvas.translate(offsetX.toPx() - it, offsetY.toPx() - it)
            }
            canvas.drawOutline(shadowOutline, paint)
            canvas.restore()
        }
    }
}
