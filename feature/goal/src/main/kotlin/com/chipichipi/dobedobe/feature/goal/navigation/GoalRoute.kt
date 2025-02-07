package com.chipichipi.dobedobe.feature.goal.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface GoalRoute {
    @Serializable
    data class Detail(val id: Long) : GoalRoute

    @Serializable
    data object Add : GoalRoute

    @Serializable
    data object Search : GoalRoute
}
