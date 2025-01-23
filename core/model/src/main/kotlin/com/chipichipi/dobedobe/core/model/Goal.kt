package com.chipichipi.dobedobe.core.model

data class Goal(
    val id: Long,
    val title: String,
    val isPinned: Boolean,
    val state: State,
) {
    val isDone: Boolean
        get() = state == State.Done

    enum class State {
        Todo,
        Done,
    }
}
