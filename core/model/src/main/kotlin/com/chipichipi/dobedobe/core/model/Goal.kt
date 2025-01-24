package com.chipichipi.dobedobe.core.model

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
}
