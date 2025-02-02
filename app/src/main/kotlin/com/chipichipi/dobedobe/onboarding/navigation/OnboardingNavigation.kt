package com.chipichipi.dobedobe.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.chipichipi.dobedobe.onboarding.OnboardingAddGoalRoute
import com.chipichipi.dobedobe.onboarding.OnboardingSelectCharacterRoute
import kotlinx.serialization.Serializable

@Serializable
internal data object OnboardingAddGoalRoute

@Serializable
internal data object OnboardingSelectCharacterRoute

internal fun NavController.navigateToSelectCharacter(
    navOptions: NavOptions? = null,
) = navigate(route = OnboardingSelectCharacterRoute, navOptions)

internal fun NavGraphBuilder.onboardingAddGoalScreen(
    navigateToSelectCharacter: () -> Unit,
) {
    composable<OnboardingAddGoalRoute> {
        OnboardingAddGoalRoute(
            navigateToSelectCharacter = navigateToSelectCharacter
        )
    }
}

internal fun NavGraphBuilder.onboardingSelectCharacterScreen() {
    composable<OnboardingSelectCharacterRoute> {
        OnboardingSelectCharacterRoute()
    }
}
