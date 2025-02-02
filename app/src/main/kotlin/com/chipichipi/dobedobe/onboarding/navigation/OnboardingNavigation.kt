package com.chipichipi.dobedobe.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.chipichipi.dobedobe.onboarding.OnboardingAddGoalRoute
import com.chipichipi.dobedobe.onboarding.OnboardingSelectCharacterRoute
import kotlinx.serialization.Serializable

@Serializable
internal data object OnboardingAddGoalRoute

@Serializable
internal data object OnboardingSelectCharacterRoute

internal fun NavGraphBuilder.onboardingAddGoalScreen() {
    composable<OnboardingAddGoalRoute> {
        OnboardingAddGoalRoute()
    }
}

internal fun NavGraphBuilder.onboardingSelectCharacterScreen() {
    composable<OnboardingSelectCharacterRoute> {
        OnboardingSelectCharacterRoute()
    }
}
