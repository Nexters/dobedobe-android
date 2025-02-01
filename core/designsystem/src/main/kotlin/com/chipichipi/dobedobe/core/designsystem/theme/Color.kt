package com.chipichipi.dobedobe.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
class DobeDobeColors(isDarkTheme: Boolean = false) {
    val gray50 = Color(0xFFF9FAFB)
    val gray100 = Color(0xFFF3F4F6)
    val gray200 = Color(0xFFE5E7EB)
    val gray300 = Color(0xFFC0C4CA)
    val gray400 = Color(0xFF9CA3AF)
    val gray500 = Color(0xFF7A828C)
    val gray600 = Color(0xFF5F6875)
    val gray700 = Color(0xFF48505C)
    val gray800 = Color(0xFF333945)
    val gray900 = Color(0xFF262C36)

    val green1 = Color(0xFFF3FFF1)
    val green2 = Color(0xFF00FF84)
    val green3 = Color(0xFF00B35D)

    val white = Color.White
    val black = Color.Black

    val red = Color(0xFFFF354D)
}

internal val LocalDobeDobeColors = staticCompositionLocalOf { DobeDobeColors() }
