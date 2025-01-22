package com.chipichipi.dobedobe.feature.dashboard.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.chipichipi.dobedobe.core.model.Goal
import kotlinx.datetime.Instant

internal class GoalPreviewParameterProvider : PreviewParameterProvider<List<Goal>> {
    override val values: Sequence<List<Goal>>
        get() =
            sequenceOf(
                fakeGoals(10),
                fakeGoals(3),
                fakeGoals(2),
                fakeGoals(1),
                emptyList(),
            )

    companion object {
        private val fakeGoals: List<Goal> =
            listOf(
                Goal(
                    id = 1,
                    title = "3대 500 달성하기",
                    isPinned = true,
                    state = Goal.State.Todo,
                    createdAt = Instant.DISTANT_PAST,
                    completedAt = null,
                ),
                Goal(
                    id = 2,
                    title = "영어 단어 하루 10개 외우기",
                    isPinned = false,
                    state = Goal.State.Done,
                    createdAt = Instant.DISTANT_PAST,
                    completedAt = Instant.DISTANT_PAST,
                ),
                Goal(
                    id = 3,
                    title = "두비두비 출시",
                    isPinned = false,
                    state = Goal.State.Todo,
                    createdAt = Instant.DISTANT_PAST,
                    completedAt = null,
                ),
                Goal(
                    id = 4,
                    title = "주간 러닝 3회",
                    isPinned = true,
                    state = Goal.State.Todo,
                    createdAt = Instant.DISTANT_PAST,
                    completedAt = null,
                ),
                Goal(
                    id = 5,
                    title = "하루 2L 물 마시기",
                    isPinned = false,
                    state = Goal.State.Todo,
                    createdAt = Instant.DISTANT_PAST,
                    completedAt = null,
                ),
                Goal(
                    id = 6,
                    title = "책 5권 읽기",
                    isPinned = false,
                    state = Goal.State.Todo,
                    createdAt = Instant.DISTANT_PAST,
                    completedAt = null,
                ),
                Goal(
                    id = 7,
                    title = "일본 여행 다녀오기",
                    isPinned = false,
                    state = Goal.State.Todo,
                    createdAt = Instant.DISTANT_PAST,
                    completedAt = null,
                ),
                Goal(
                    id = 8,
                    title = "운동 루틴 정착",
                    isPinned = true,
                    state = Goal.State.Todo,
                    createdAt = Instant.DISTANT_PAST,
                    completedAt = null,
                ),
            )

        fun fakeGoals(size: Int): List<Goal> =
            List(size) {
                fakeGoals[it % fakeGoals.size].copy(id = it.toLong())
            }
    }
}
