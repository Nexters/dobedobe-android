package com.chipichipi.dobedobe.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp

@Immutable
data class DobeDobeTypography internal constructor(
    val title1: TextStyle,
    val title2: TextStyle,
    val heading1: TextStyle,
    val heading2: TextStyle,
    val body1: TextStyle,
    val body2: TextStyle,
    val body3: TextStyle,
    val caption1: TextStyle,
    val caption2: TextStyle,
) {
    constructor(
        systemFontFamily: FontFamily = FontFamily.Default,
        title1: TextStyle = TextStyle(
            fontFamily = systemFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            lineHeight = 42.sp,
            letterSpacing = -(0.504).sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Proportional,
                trim = LineHeightStyle.Trim.None,
            ),
        ),
        title2: TextStyle = TextStyle(
            fontFamily = systemFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 36.sp,
            letterSpacing = -(0.432).sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Proportional,
                trim = LineHeightStyle.Trim.None,
            ),
        ),
        heading1: TextStyle = TextStyle(
            fontFamily = systemFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            lineHeight = 33.sp,
            letterSpacing = -(0.396).sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Proportional,
                trim = LineHeightStyle.Trim.None,
            ),
        ),
        heading2: TextStyle = TextStyle(
            fontFamily = systemFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 17.sp,
            lineHeight = 25.5.sp,
            letterSpacing = -(0.306).sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Proportional,
                trim = LineHeightStyle.Trim.None,
            ),
        ),
        body1: TextStyle = TextStyle(
            fontFamily = systemFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = -(0.288).sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Proportional,
                trim = LineHeightStyle.Trim.None,
            ),
        ),
        body2: TextStyle = TextStyle(
            fontFamily = systemFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            lineHeight = 22.5.sp,
            letterSpacing = -(0.27).sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Proportional,
                trim = LineHeightStyle.Trim.None,
            ),
        ),
        body3: TextStyle = TextStyle(
            fontFamily = systemFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 21.sp,
            letterSpacing = -(0.252).sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Proportional,
                trim = LineHeightStyle.Trim.None,
            ),
        ),
        caption1: TextStyle = TextStyle(
            fontFamily = systemFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            letterSpacing = -(0.216).sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Proportional,
                trim = LineHeightStyle.Trim.None,
            ),
        ),
        caption2: TextStyle = TextStyle(
            fontFamily = systemFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp,
            lineHeight = 15.sp,
            letterSpacing = -(0.18).sp,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Proportional,
                trim = LineHeightStyle.Trim.None,
            ),
        ),
    ) : this(
        title1 = title1,
        title2 = title2,
        heading1 = heading1,
        heading2 = heading2,
        body1 = body1,
        body2 = body2,
        body3 = body3,
        caption1 = caption1,
        caption2 = caption2,
    )
}

internal val LocalDobeDobeTypography = staticCompositionLocalOf { DobeDobeTypography() }
