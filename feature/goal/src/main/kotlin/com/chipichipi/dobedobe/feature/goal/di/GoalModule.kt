package com.chipichipi.dobedobe.feature.goal.di

import com.chipichipi.dobedobe.feature.goal.AddGoalViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val goalModule =
    module {
        viewModelOf(::AddGoalViewModel)
    }
