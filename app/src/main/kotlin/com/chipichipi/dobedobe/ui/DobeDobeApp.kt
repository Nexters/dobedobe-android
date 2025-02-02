package com.chipichipi.dobedobe.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Short
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeBackground
import com.chipichipi.dobedobe.core.designsystem.component.DobeDobeSnackbar
import com.chipichipi.dobedobe.navigation.DobeDobeNavHost
import com.chipichipi.dobedobe.navigation.OnboardingNavHost
import com.chipichipi.dobedobe.onboarding.OnboardingAddGoalRoute
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun DobeDobeApp(
    appState: DobeDobeAppState,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel(),
) {
    val mainUiState by viewModel.mainUiState.collectAsStateWithLifecycle()

    DobeDobeBackground(
        modifier = modifier,
    ) {
        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(
            modifier = modifier,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState,
                    snackbar = {
                        DobeDobeSnackbar(
                            snackbarData = it,
                            modifier = Modifier
                                .navigationBarsPadding()
                                .padding(bottom = 32.dp),
                        )
                    },
                )
            },
        ) { padding ->
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal,
                        ),
                    ),
            ) {
                when (mainUiState) {
                    is MainUiState.Loading -> Unit
                    is MainUiState.Onboarding -> {
                        OnboardingNavHost()
                    }
                    is MainUiState.Success -> {
                        DobeDobeNavHost(
                            modifier = modifier,
                            appState = appState,
                            onShowSnackbar = { message, action ->
                                snackbarHostState.showSnackbar(
                                    message = message,
                                    actionLabel = action,
                                    duration = Short,
                                ) == ActionPerformed
                            },
                        )
                    }
                }
            }
        }
    }
}
