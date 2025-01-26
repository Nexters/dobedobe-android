package com.chipichipi.dobedobe.core.data.repository

import com.chipichipi.dobedobe.core.database.dao.GoalDao
import com.chipichipi.dobedobe.core.database.entity.GoalEntity
import com.chipichipi.dobedobe.core.model.Goal
import com.chipichipi.dobedobe.core.model.Goals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class GoalRepositoryImpl(
    private val goalDao: GoalDao,
) : GoalRepository {
    private val goals: Flow<Goals> = goalDao.getGoals()
        .map { goals -> goals.map(GoalEntity::toModel) }
        .map(::Goals)

    override fun getSortedGoals(): Flow<List<Goal>> = goals.map(Goals::sorted)

    override fun getGoal(id: Long): Flow<Goal> = goals.map { it.find(id) }

    override suspend fun addGoal(title: String): Result<Unit> {
        return runCatching {
            val newGoal: GoalEntity = Goal.todo(title = title).toEntity()
            goalDao.insertGoal(newGoal)
        }
    }

    override suspend fun removeGoal(id: Long): Result<Unit> {
        return runCatching {
            val goals: Goals = goals.firstOrNull() ?: error("Goals empty")
            goals.find(id)
            goalDao.deleteGoal(id)
        }
    }

    override suspend fun togglePin(id: Long): Result<Unit> {
        return runCatching {
            val goals: Goals = goals.firstOrNull() ?: error("Goals empty")
            val toggledGoal: Goal = goals.togglePin(id).find(id)
            goalDao.updateGoal(toggledGoal.toEntity())
        }
    }

    override suspend fun toggleCompletion(id: Long): Result<Unit> {
        return runCatching {
            val goals: Goals = goals.firstOrNull() ?: error("Goals empty")
            val toggledGoal: Goal = goals.toggleCompletion(id).find(id)
            goalDao.updateGoal(toggledGoal.toEntity())
        }
    }
}

private fun Goal.toEntity(): GoalEntity {
    return GoalEntity(
        id = id,
        title = title,
        isPinned = isPinned,
        isCompleted = isCompleted,
        createdAt = createdAt,
        completedAt = completedAt,
    )
}

private fun GoalEntity.toModel(): Goal {
    return Goal(
        id = id,
        title = title,
        isPinned = isPinned,
        isCompleted = isCompleted,
        createdAt = createdAt,
        completedAt = completedAt,
    )
}
