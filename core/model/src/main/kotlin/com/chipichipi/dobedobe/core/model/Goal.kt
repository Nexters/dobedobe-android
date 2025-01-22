package com.chipichipi.dobedobe.core.model

data class Goal(
    val id: Long,
    val title: String,
    val isPinned: Boolean,
    val state: State,
) {
    enum class State {
        Todo,
        Doing,
        Done, // TODO: Goal State 기능 확정나면 수정
    }
}

private val fakeGoals: List<Goal> =
    listOf(
        Goal(1, "3대 500 달성하기", true, Goal.State.Todo),
        Goal(2, "영어 단어 하루 10개 외우기", false, Goal.State.Done),
        Goal(3, "두비두비 출시", false, Goal.State.Doing),
        Goal(4, "주간 러닝 3회", true, Goal.State.Todo),
        Goal(5, "하루 2L 물 마시기", false, Goal.State.Todo),
        Goal(6, "책 5권 읽기", false, Goal.State.Todo),
        Goal(7, "일본 여행 다녀오기", false, Goal.State.Done),
        Goal(8, "운동 루틴 정착", true, Goal.State.Done),
    )

fun fakeGoals(size: Int): List<Goal> =
    List(size) {
        fakeGoals[it % fakeGoals.size].copy(id = it.toLong())
    }
