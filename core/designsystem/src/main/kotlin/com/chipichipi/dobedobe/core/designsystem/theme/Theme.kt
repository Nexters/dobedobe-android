package com.chipichipi.dobedobe.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

// TODO : change Light/Dark color
private val LightAndroidBackgroundTheme = BackgroundTheme(color = Color.White)
private val DarkAndroidBackgroundTheme = BackgroundTheme(color = Color.White)

object DobeDobeTheme {
    val typography: DobeDobeTypography
        @ReadOnlyComposable
        @Composable
        get() = LocalDobeDobeTypography.current

    val colors: DobeDobeColors
        @ReadOnlyComposable
        @Composable
        get() = LocalDobeDobeColors.current
}

@Composable
fun DobeDobeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val backgroundTheme = if (darkTheme) DarkAndroidBackgroundTheme else LightAndroidBackgroundTheme
    val typography = remember { DobeDobeTypography() }
    val colors = remember { DobeDobeColors(darkTheme) }

    CompositionLocalProvider(
        LocalBackgroundTheme provides backgroundTheme,
        LocalDobeDobeTypography provides typography,
        LocalDobeDobeColors provides colors,
    ) {
        MaterialTheme(
            content = content,
        )
    }
}
