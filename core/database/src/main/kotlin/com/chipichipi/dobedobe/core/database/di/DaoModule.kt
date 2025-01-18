package com.chipichipi.dobedobe.core.database.di

import org.koin.dsl.module

val daoModule =
    module {
        includes(databaseModule)
    }
