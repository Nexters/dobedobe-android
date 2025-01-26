package com.chipichipi.dobedobe.core.model

import com.chipichipi.dobedobe.core.model.fixtures.fakeGoal
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class GoalsTest {
    @Test
    fun `Goal ID 는 중복될 수 없다`() {
        // given
        val goals = listOf(fakeGoal(id = 1), fakeGoal(id = 1))

        // when & then
        shouldThrow<IllegalArgumentException> {
            Goals(goals)
        }
    }

    @Test
    fun `Goal은 pinned 된 순서대로 정렬된다`() {
        // given
        val goals = Goals(
            listOf(
                fakeGoal(id = 1, isPinned = true),
                fakeGoal(id = 2, isPinned = false),
            ),
        )
        // when
        val actualIds = goals.sorted().map { it.id }
        // then
        val expectIds: List<Long> = listOf(1, 2)
        actualIds shouldBe expectIds
    }

    @Test
    fun `Goal은 pinned, todo 순서대로 정렬된다`() {
        // given
        val goals = Goals(
            listOf(
                fakeGoal(id = 1, isPinned = true),
                fakeGoal(id = 2, isPinned = true, isCompleted = true, completedAt = 1),
            ),
        )
        // when
        val actualIds = goals.sorted().map { it.id }
        // then
        val expectIds: List<Long> = listOf(1, 2)
        actualIds shouldBe expectIds
    }

    @Test
    fun `Goal 은 Pinned, todo, 최근에 생성된 순서대로 정렬된다`() {
        // given
        val goals = Goals(
            listOf(
                fakeGoal(id = 1, isPinned = true, createdAt = 1),
                fakeGoal(id = 2, isPinned = false, createdAt = 2),
                fakeGoal(
                    id = 3,
                    isPinned = true,
                    isCompleted = true,
                    createdAt = 3,
                    completedAt = 4,
                ),
                fakeGoal(id = 4, isPinned = false, createdAt = 4),
            ),
        )
        // when
        val actualIds = goals.sorted().map { it.id }

        // then
        val expectIds: List<Long> = listOf(1, 3, 4, 2)
        actualIds shouldBe expectIds
    }
}
