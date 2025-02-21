package com.chipichipi.dobedobe.feature.goal

enum class GoalSnackBarType {
    IDLE,
    ADD,
    EDIT,
    DELETE,
    ;

    companion object {
        const val KEY: String = "DashBoardSnackBarType"
    }
}
