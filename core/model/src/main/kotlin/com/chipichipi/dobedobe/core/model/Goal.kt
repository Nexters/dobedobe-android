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
    val isDone: Boolean
        get() = (state == State.Done)

    init {
        if (isCompleted) {
            require(completedAt != null) { "completedAt should not be null when isCompleted is true" }
        } else {
            require(completedAt == null) { "completedAt should be null when isCompleted is false" }
        }
    }

    companion object {
        private val NO_ID = 0L
        // TODO: 부생성자로 할지, 팩토리 메서드로 할지 고민중..
        fun newTodo(title: String): Goal {
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
