package com.chipichipi.dobedobe.core.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class Goal(
    val id: Long,
    val title: String,
    val isPinned: Boolean,
    val isCompleted: Boolean,
    val createdAt: Instant,
    val completedAt: Instant?,
) {
    init {
        if (isCompleted) {
            require(completedAt != null) { "completedAt should not be null when isCompleted is true" }
        } else {
            require(completedAt == null) { "completedAt should be null when isCompleted is false" }
        }
    }

    companion object {
        private const val NO_ID = 0L

        fun todo(title: String): Goal {
            return Goal(
                id = NO_ID,
                title = title,
                isPinned = false,
                isCompleted = false,
                createdAt = Clock.System.now(),
                completedAt = null,
            )
        }
    }
}
