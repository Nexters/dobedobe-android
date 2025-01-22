package com.chipichipi.dobedobe.core.model

data class Goal(
    val id: Long,
    val title: String,
    val isPinned: Boolean,
    val state: State,
) {
    enum class State {
        Todo, Doing, Done
    }
}
