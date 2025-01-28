package com.chipichipi.dobedobe.core.database.di

import com.chipichipi.dobedobe.core.database.dao.DashboardPhotoDao
import com.chipichipi.dobedobe.core.database.dao.GoalDao
import com.chipichipi.dobedobe.core.database.db.DobeDobeDatabase
import org.koin.dsl.module

val daoModule =
    module {
        includes(databaseModule)

        single<GoalDao> { get<DobeDobeDatabase>().goalDao() }
        single<DashboardPhotoDao> { get<DobeDobeDatabase>().dashboardPhotoDao() }
    }
