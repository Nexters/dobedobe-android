package com.chipichipi.dobedobe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.chipichipi.dobedobe.core.designsystem.theme.DobeDobeTheme
import com.chipichipi.dobedobe.ui.DobeDobeApp
import com.chipichipi.dobedobe.ui.rememberDobeDobeAppState
import org.koin.androidx.compose.KoinAndroidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

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
