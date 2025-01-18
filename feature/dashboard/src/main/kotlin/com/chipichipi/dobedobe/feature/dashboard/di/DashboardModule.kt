package com.chipichipi.dobedobe.feature.dashboard.di

import com.chipichipi.dobedobe.feature.dashboard.DashboardViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val dashBoardModule = module {
    viewModelOf(::DashboardViewModel)
}