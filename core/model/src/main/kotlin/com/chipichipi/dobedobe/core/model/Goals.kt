package com.chipichipi.dobedobe.core.model

@JvmInline
value class Goals(private val value: List<Goal>) {
    init {
        require(value.distinctBy { it.id }.size == value.size) {
            "goal should have unique id"
        }
    }

    fun sorted(): List<Goal> {
        return value.sortedWith(
            compareBy(
                { it.pinComparable() },
                { it.createdAtComparable() },
            ),
        )
    }

    fun find(id: Long): Goal {
        checkGoalExists(id)
        return value.first { it.id == id }
    }

    fun toggleCompletion(id: Long): Goals {
        checkGoalExists(id)
        return Goals(
            value.map { goal ->
                if (goal.id == id) {
                    goal.copy(isCompleted = !goal.isCompleted)
                } else {
                    goal
                }
            },
        )
    }

    fun remove(id: Long): Goals {
        checkGoalExists(id)
        return Goals(value.filter { it.id != id })
    }

    fun togglePin(id: Long): Goals {
        checkGoalExists(id)
        return Goals(
            value.map { goal ->
                if (goal.id == id) {
                    goal.copy(isPinned = !goal.isPinned)
                } else {
                    goal
                }
            },
        )
    }

    private fun checkGoalExists(id: Long) {
        require(value.any { it.id == id }) {
            "Goal with id $id not found"
        }
    }

    private fun Goal.pinComparable(): Int {
        return if (isPinned) 0 else 1
    }

    private fun Goal.createdAtComparable(): Long {
        return -createdAt.toEpochMilliseconds()
    }
}
