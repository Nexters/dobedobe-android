package com.chipichipi.dobedobe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.ui.DobeDobeApp
import com.chipichipi.dobedobe.ui.MainUiState
import com.chipichipi.dobedobe.ui.MainViewModel
import com.chipichipi.dobedobe.ui.rememberDobeDobeAppState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainUiState.collect {}
            }
        }

        splashScreen.setKeepOnScreenCondition {
            viewModel.mainUiState.value is MainUiState.Loading
        }

        setContent {
            val appState = rememberDobeDobeAppState()

            DobeDobeTheme {
                KoinAndroidContext {
                    DobeDobeApp(
                        appState = appState,
                    )
                }
            }
        }
    }
}
