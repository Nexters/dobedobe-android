package com.chipichipi.dobedobe.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeBackground

@Composable
internal fun DobeDobeApp(
    appState: DobeDobeAppState,
    modifier: Modifier = Modifier,
) {
    DobeDobeBackground(
        modifier = modifier,
    ) {
        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(
            modifier = modifier,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { padding ->
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding)
                    .windowInsetsPadding(WindowInsets.safeDrawing),
            ) {
                Text(
                    text = "TEST",
                )
            }
        }
    }
}
