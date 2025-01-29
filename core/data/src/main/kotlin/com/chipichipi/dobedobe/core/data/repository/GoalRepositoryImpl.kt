package com.chipichipi.dobedobe.core.data.repository

import com.chipichipi.dobedobe.core.database.dao.GoalDao
import com.chipichipi.dobedobe.core.database.entity.GoalEntity
import com.chipichipi.dobedobe.core.database.entity.toEntity
import com.chipichipi.dobedobe.core.database.entity.toModel
import com.chipichipi.dobedobe.core.model.Goal
import com.chipichipi.dobedobe.core.model.Goals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

internal class GoalRepositoryImpl(
    private val goalDao: GoalDao,
) : GoalRepository {
    private val goals: Flow<Goals> = goalDao.getGoals()
        .map { goals -> goals.map(GoalEntity::toModel) }
        .map(::Goals)

    override fun getSortedGoals(): Flow<List<Goal>> = goals.map(Goals::sorted)

    override suspend fun getRandomTodoGoal(): Result<Goal> {
        return runCatching {
            val goals: Goals =
                goals.firstOrNull() ?: error("if Goals empty, cannot get random todo goal")
            val todoGoals: List<Goal> = goals.getTodoGoals()
            todoGoals.random()
        }
    }

    override fun getGoal(id: Long): Flow<Goal> = goalDao.getGoal(id).map(GoalEntity::toModel)

    override suspend fun addGoal(title: String): Result<Unit> {
        return runCatching {
            val newGoal: GoalEntity = Goal.todo(title = title).toEntity()
            goalDao.insertGoal(newGoal)
        }
    }

    override suspend fun removeGoal(id: Long): Result<Unit> {
        return runCatching {
            val goals: Goals = goals.firstOrNull() ?: error("if Goals empty, cannot remove goal")
            goals.checkGoalExists(id)
            goalDao.deleteGoal(id)
        }
    }

    override suspend fun togglePin(id: Long): Result<Unit> {
        return runCatching {
            val goals: Goals = goals.firstOrNull() ?: error("if Goals empty, cannot toggle pin")
            val toggledGoal: Goal = goals.togglePin(id).find(id)
            goalDao.updateGoal(toggledGoal.toEntity())
        }
    }

    override suspend fun toggleCompletion(id: Long): Result<Unit> {
        return runCatching {
            val goals: Goals =
                goals.firstOrNull() ?: error("if Goals empty, cannot toggle completion")
            val toggledGoal: Goal = goals.toggleCompletion(id).find(id)
            goalDao.updateGoal(toggledGoal.toEntity())
        }
    }

    override suspend fun updateGoal(goal: Goal): Result<Unit> {
        return runCatching {
            val goals = goals.firstOrNull() ?: error("if Goals empty, cannot update goal")
            goals.checkGoalExists(goal.id)
            goalDao.updateGoal(goal.toEntity())
        }
    }
}
