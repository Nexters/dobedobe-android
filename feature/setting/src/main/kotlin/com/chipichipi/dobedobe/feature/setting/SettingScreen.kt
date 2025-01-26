package com.chipichipi.dobedobe.feature.setting

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = koinViewModel()
) {
    SettingScreen(
        modifier = modifier
    )
}

@Composable
private fun SettingScreen(
    modifier: Modifier = Modifier
) {

}
