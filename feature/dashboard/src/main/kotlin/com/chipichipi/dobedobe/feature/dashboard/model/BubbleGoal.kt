package com.chipichipi.dobedobe.feature.dashboard.model

import com.chipichipi.dobedobe.core.model.Goal

data class BubbleGoal(
    val title: String,
    val id: Long?,
) {
    companion object {
        private val Empty = BubbleGoal(title = "", id = null)

        fun empty(): BubbleGoal = Empty

        fun from(goal: Goal): BubbleGoal {
            return BubbleGoal(goal.title, goal.id)
        }
    }
}
