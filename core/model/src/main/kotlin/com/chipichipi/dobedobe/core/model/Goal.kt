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
        require(title.isNotBlank()) { "title should not be blank" }
        require(title.length <= MAX_TITLE_LENGTH) { "title should not exceed $MAX_TITLE_LENGTH characters" }
    }

    fun toggleCompletion(): Goal {
        return if (isCompleted) {
            copy(isCompleted = false, completedAt = null)
        } else {
            copy(isCompleted = true, completedAt = Clock.System.now())
        }
    }

    fun togglePin(): Goal {
        return copy(isPinned = !isPinned)
    }

    companion object {
        private const val NO_ID = 0L
        private const val MAX_TITLE_LENGTH = 20

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

        fun validateTitle(title: String): GoalTitleValidResult {
            return when {
                title.isEmpty() -> GoalTitleValidResult.Empty
                title.isBlank() -> GoalTitleValidResult.Blank
                title.length > MAX_TITLE_LENGTH -> GoalTitleValidResult.TooLong
                else -> GoalTitleValidResult.Valid
            }
        }
    }
}
