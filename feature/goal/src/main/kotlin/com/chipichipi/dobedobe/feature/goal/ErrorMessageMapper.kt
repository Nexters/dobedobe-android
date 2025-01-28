package com.chipichipi.dobedobe.feature.goal

import com.chipichipi.dobedobe.core.model.GoalTitleValidResult

// TODO: stringResource 적용 해야함
fun GoalTitleValidResult.errorMessage(): String? = when (this) {
    GoalTitleValidResult.Valid, GoalTitleValidResult.Empty -> null
    GoalTitleValidResult.Blank -> "빈 목표는 입력할 수 없어요."
    GoalTitleValidResult.TooLong -> "띄어쓰기 포함 20자까지만 쓸 수 있어요"
}
