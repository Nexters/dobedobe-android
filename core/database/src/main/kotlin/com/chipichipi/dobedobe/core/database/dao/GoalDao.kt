package com.chipichipi.dobedobe.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.chipichipi.dobedobe.core.database.entity.GoalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Query("SELECT * FROM Goal")
    fun getGoals(): Flow<List<GoalEntity>>

    @Query("SELECT * FROM Goal WHERE id = :id")
    fun getGoal(id: Long): Flow<GoalEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goals: GoalEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoals(goals: List<GoalEntity>)

    @Query("DELETE FROM Goal WHERE id = :id")
    suspend fun deleteGoal(id: Long)

    @Update
    suspend fun updateGoal(goal: GoalEntity)

    @Query("DELETE FROM Goal")
    suspend fun clear()
}
