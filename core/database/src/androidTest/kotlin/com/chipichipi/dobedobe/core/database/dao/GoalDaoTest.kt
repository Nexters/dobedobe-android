package com.chipichipi.dobedobe.core.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.chipichipi.dobedobe.core.database.db.DobeDobeDatabase
import com.chipichipi.dobedobe.core.database.entity.GoalEntity
import com.chipichipi.dobedobe.core.database.fixtures.fakeGoalEntities
import com.chipichipi.dobedobe.core.database.fixtures.fakeGoalEntity
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import org.junit.Before
import kotlin.test.Test

class GoalDaoTest {
    private lateinit var goalDao: GoalDao

    @Before
    fun setUp() {
        goalDao =
            Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                DobeDobeDatabase::class.java,
            ).build().goalDao()
    }

    @Test
    fun 목표_저장_테스트() =
        runTest {
            // given
            val goals: List<GoalEntity> = fakeGoalEntities("준원", "준혁")

            // when
            goalDao.insertGoals(goals)

            // then
            val retrievedGoals: List<GoalEntity> = goalDao.getGoals().first()
            val retrievedGoalTitles = retrievedGoals.map { it.title }
            retrievedGoals.shouldHaveSize(2)
            retrievedGoalTitles shouldBe listOf("준원", "준혁")
        }

    @Test
    fun 목표_저장후_삭제_테스트() =
        runTest {
            // given
            val goal: GoalEntity = fakeGoalEntity(id = 1L, title = "준원")

            // when
            goalDao.insertGoal(goal)
            goalDao.deleteGoal(id = 1L)
            // then
            val retrievedGoals: List<GoalEntity> = goalDao.getGoals().first()
            retrievedGoals.shouldHaveSize(0)
        }

    @Test
    fun 목표_완료하기_테스트() =
        runTest {
            // given
            val goal: GoalEntity = fakeGoalEntity(id = 1L, title = "준원", isCompleted = false)
            // when
            goalDao.insertGoal(goal)
            goalDao.updateGoal(goal.copy(isCompleted = true, completedAt = Instant.DISTANT_FUTURE))
            // then
            val retrievedGoal: GoalEntity? = goalDao.getGoal(1L).firstOrNull()
            retrievedGoal.shouldNotBeNull()
            retrievedGoal.isCompleted.shouldBeTrue()
        }
}
