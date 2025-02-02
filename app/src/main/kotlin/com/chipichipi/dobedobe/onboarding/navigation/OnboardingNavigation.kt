package com.chipichipi.dobedobe.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.chipichipi.dobedobe.onboarding.OnboardingAddGoalRoute
import kotlinx.serialization.Serializable

@Serializable
internal data object OnboardingAddGoalRoute

internal fun NavGraphBuilder.onboardingAddGoalScreen() {
    composable<OnboardingAddGoalRoute> {
        OnboardingAddGoalRoute()
    }
}
