package com.chipichipi.dobedobe.core.model

import com.chipichipi.dobedobe.core.model.fixtures.fakeGoal
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.booleans.shouldNotBeTrue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class GoalTest {
    @Test
    fun `완료 목표는 완료 날짜가 없으면 예외 발생`() {
        shouldThrow<IllegalArgumentException> {
            fakeGoal(id = 0, isCompleted = true, completedAt = null)
        }
    }

    @Test
    fun `새로운 목표는 완료 날짜가 있으면 예외 발생`() {
        shouldThrow<IllegalArgumentException> {
            fakeGoal(id = 0, isCompleted = false, completedAt = 10)
        }
    }

    @Test
    fun `새로운 목표를 생성할 수 있다`() {
        // given
        val title = "테스트 목표"

        // when
        val goal = Goal.todo(title)

        // then
        goal.id shouldBe 0L
        goal.isCompleted.shouldNotBeTrue()
        goal.completedAt.shouldBeNull()
    }

    @Test
    fun `목표를 완료하면 생성 시간이 기록된다`() {
        // given
        val todoGoal = Goal.todo("테스트")
        // when
        val completedGoal = todoGoal.toggleCompletion()
        // then
        completedGoal.isCompleted.shouldBeTrue()
        completedGoal.completedAt.shouldNotBeNull()
    }

    @Test
    fun `완료된 목표를 다시 Todo 로 변경하면 완료 시간이 없어진다`() {
        // given
        val completedGoal = fakeGoal(id = 0, isCompleted = true, completedAt = 10)
        // when
        val todoGoal = completedGoal.toggleCompletion()
        // then
        todoGoal.isCompleted.shouldBeFalse()
        todoGoal.completedAt.shouldBeNull()
    }

    @Test
    fun `목표는 20자 이내의 제목을 가져야 한다`() {
        shouldThrow<IllegalArgumentException> {
            Goal.todo("123456789012345678901")
        }
    }

    @Test
    fun `목표는 빈 제목을 가질 수 없다`() {
        shouldThrow<IllegalArgumentException> {
            Goal.todo(" ")
        }
    }
}
