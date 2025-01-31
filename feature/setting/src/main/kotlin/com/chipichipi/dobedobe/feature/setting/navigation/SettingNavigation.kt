package com.chipichipi.dobedobe.feature.setting.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.chipichipi.dobedobe.feature.setting.SettingRoute
import kotlinx.serialization.Serializable

@Serializable
data object SettingRoute

fun NavController.navigateToSetting(
    navOptions: NavOptions? = null,
) = navigate(route = SettingRoute, navOptions)

fun NavGraphBuilder.settingScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToBack: (NavBackStackEntry) -> Unit,
) {
    composable<SettingRoute> { from ->
        SettingRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToBack = { navigateToBack(from) },
        )
    }
}
