package com.chipichipi.dobedobe.core.data.repository

import com.chipichipi.dobedobe.core.model.Goal
import kotlinx.coroutines.flow.Flow

interface GoalRepository {
    fun getSortedGoals(): Flow<List<Goal>>

    suspend fun getTodoGoals(): Result<List<Goal>>

    fun getGoal(id: Long): Flow<Goal>

    suspend fun addGoal(title: String): Result<Unit>

    suspend fun removeGoal(id: Long): Result<Unit>

    suspend fun changeGoalTitle(id: Long, title: String): Result<Unit>

    suspend fun togglePin(id: Long): Result<Unit>

    suspend fun toggleCompletion(id: Long): Result<Unit>

    suspend fun updateGoal(goal: Goal): Result<Unit>
}
