package com.chipichipi.dobedobe.core.model

import kotlinx.datetime.Instant

data class Goal(
    val id: Long,
    val title: String,
    val isPinned: Boolean,
    val state: State,
    val createdAt: Instant,
    val completedAt: Instant?,
) {
    init {
        when (state) {
            State.Todo -> require(completedAt == null) { "$state should not have completedAt" }
            State.Done -> require(completedAt != null) { "$state should have completedAt" }
        }
    }

    val isDone: Boolean
        get() = state == State.Done

    enum class State {
        Todo,
        Done,
    }
}
