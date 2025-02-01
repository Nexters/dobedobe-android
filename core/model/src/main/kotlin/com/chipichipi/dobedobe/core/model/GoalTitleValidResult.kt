package com.chipichipi.dobedobe.core.model

sealed interface GoalTitleValidResult {
    data object Valid : GoalTitleValidResult

    data object Empty : GoalTitleValidResult

    data object Blank : GoalTitleValidResult

    data object TooLong : GoalTitleValidResult

    fun isValid(): Boolean {
        return this is Valid
    }
}
