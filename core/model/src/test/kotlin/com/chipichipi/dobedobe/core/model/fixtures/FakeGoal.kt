package com.chipichipi.dobedobe.core.model.fixtures

import com.chipichipi.dobedobe.core.model.Goal
import kotlinx.datetime.Instant

fun fakeGoal(
    id: Long,
    title: String = "title",
    isPinned: Boolean = false,
    isCompleted: Boolean = false,
    createdAt: Long = 0L,
    completedAt: Long? = null,
): Goal {
    return Goal(
        id = id,
        title = title,
        isPinned = isPinned,
        isCompleted = isCompleted,
        createdAt = Instant.fromEpochMilliseconds(createdAt),
        completedAt = completedAt?.let { Instant.fromEpochMilliseconds(it) },
    )
}
