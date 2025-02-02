package com.chipichipi.dobedobe.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.chipichipi.dobedobe.onboarding.navigation.OnboardingAddGoalRoute
import com.chipichipi.dobedobe.onboarding.navigation.onboardingAddGoalScreen

@Composable
internal fun OnboardingNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = OnboardingAddGoalRoute
    ) {
        onboardingAddGoalScreen()
    }
}
