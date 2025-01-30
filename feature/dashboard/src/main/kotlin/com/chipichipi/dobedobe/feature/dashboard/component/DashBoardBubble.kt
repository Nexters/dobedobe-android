package com.chipichipi.dobedobe.feature.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.withStyle
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
        val textMeasurer = rememberTextMeasurer()
        val density = LocalDensity.current

        val maxTitleWidthPx = with(density) { 240.dp.toPx() }
        // TODO : stringResource 받아오기
        val suffix = " 해낼 거야!"

        val ellipsizedResult = textMeasurer.ellipsizeTextWithSuffix(
            title = title,
            maxWidthPx = maxTitleWidthPx,
            suffix = suffix,
            textStyle = textStyle,
        )

        val annotatedString = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    // TODO : colorScheme 적용
                    color = Color(0xFF00B35D),
                ),
            ) {
                append(ellipsizedResult.title)
            }
            append(ellipsizedResult.suffix)
        }

        Text(
            text = annotatedString,
            style = textStyle,
            maxLines = 1,
        )
    }
}

/**
 * `title`이 너무 길면 `maxWidthPx` 내에서 "..."을 붙여 잘라내고, `suffix`(해낼 거야!)는 유지
 */
private fun TextMeasurer.ellipsizeTextWithSuffix(
    title: String,
    maxWidthPx: Float,
    suffix: String,
    textStyle: TextStyle,
    ellipsis: String = "...",
): EllipsizedTextResult {
    val fullTextWidth = measure(text = title + suffix, style = textStyle).size.width

    // 1) title 이 maxWidthPx를 초과하지 않으면 그대로 반환
    if (fullTextWidth <= maxWidthPx) {
        return EllipsizedTextResult(
            title = title,
            suffix = suffix,
        )
    }

    // 2) maxWidthPx를 초과하는 경우, title ellipsis 처리
    var end = title.length
    while (end > 0) {
        val truncatedTitle = buildString {
            append(title.substring(0, end))
            append(ellipsis)
        }

        val measuredWidth = measure(text = truncatedTitle + suffix, style = textStyle).size.width

        if (measuredWidth <= maxWidthPx) {
            return EllipsizedTextResult(
                title = truncatedTitle,
                suffix = suffix,
            )
        }
        end--
    }

    return EllipsizedTextResult(
        title = title,
        suffix = suffix,
    )
}

private data class EllipsizedTextResult(
    val title: String,
    val suffix: String,
)

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
