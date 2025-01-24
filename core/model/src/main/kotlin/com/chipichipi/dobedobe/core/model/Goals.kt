package com.chipichipi.dobedobe.core.model

@JvmInline
value class Goals(private val value: List<Goal>) {
    init {
        require(value.distinctBy { it.id }.size == value.size) {
            "goal should have unique id"
        }
    }

    fun getGoals(): List<Goal> {
        return value.sortedWith(
            compareBy(
                { it.pinComparable() },
                { it.isCompleted },
                { it.createdAtComparable() },
            ),
        )
    }

    private fun Goal.pinComparable(): Int {
        return if (isPinned) 0 else 1
    }

    private fun Goal.createdAtComparable(): Long {
        return -createdAt.toEpochMilliseconds()
    }
}
