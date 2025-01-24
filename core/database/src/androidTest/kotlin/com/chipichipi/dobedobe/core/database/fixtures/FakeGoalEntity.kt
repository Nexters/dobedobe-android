package com.chipichipi.dobedobe.core.database.fixtures

import com.chipichipi.dobedobe.core.database.entity.GoalEntity
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

fun fakeGoalEntities(
    vararg titles: String,
): List<GoalEntity> {
    return titles.mapIndexed { index, title ->
        fakeGoalEntity(
            id = (index + 1).toLong(),
            title = title,
        )
    }
}

fun fakeGoalEntity(
    id: Long,
    title: String,
    isPinned: Boolean = false,
    isCompleted: Boolean = false,
    createdAt: Instant = Clock.System.now(),
    completedAt: Instant? = null,
): GoalEntity {
    return GoalEntity(
        id = id,
        title = title,
        isPinned = isPinned,
        isCompleted = isCompleted,
        createdAt = createdAt,
        completedAt = completedAt,
    )
}
