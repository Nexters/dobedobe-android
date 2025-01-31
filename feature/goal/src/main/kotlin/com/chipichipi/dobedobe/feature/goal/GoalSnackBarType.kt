package com.chipichipi.dobedobe.feature.goal


enum class GoalSnackBarType {
    IDLE,
    ADD,
    EDIT,
    REMOVE,
    ;

    companion object {
        const val KEY: String = "DashBoardSnackBarType"
    }
}