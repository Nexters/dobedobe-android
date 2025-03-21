package com.chipichipi.dobedobe.feature.goal.di

import com.chipichipi.dobedobe.feature.goal.AddGoalViewModel
import com.chipichipi.dobedobe.feature.goal.DetailGoalViewModel
import com.chipichipi.dobedobe.feature.goal.EditGoalViewModel
import com.chipichipi.dobedobe.feature.goal.SearchGoalViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val goalModule =
    module {
        viewModelOf(::AddGoalViewModel)
        viewModelOf(::DetailGoalViewModel)
        viewModelOf(::EditGoalViewModel)
        viewModelOf(::SearchGoalViewModel)
    }
