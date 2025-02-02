package com.chipichipi.dobedobe.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.chipichipi.dobedobe.onboarding.navigation.OnboardingAddGoalRoute
import com.chipichipi.dobedobe.onboarding.navigation.navigateToSelectCharacter
import com.chipichipi.dobedobe.onboarding.navigation.onboardingAddGoalScreen
import com.chipichipi.dobedobe.onboarding.navigation.onboardingSelectCharacterScreen

@Composable
internal fun OnboardingNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Vertical,
                ),
            ),
        navController = navController,
        startDestination = OnboardingAddGoalRoute
    ) {
        onboardingAddGoalScreen(
            navigateToSelectCharacter = navController::navigateToSelectCharacter
        )
        onboardingSelectCharacterScreen()
    }
}
