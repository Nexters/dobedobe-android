package com.chipichipi.dobedobe.core.data.di

import com.chipichipi.dobedobe.core.database.di.daoModule
import com.chipichipi.dobedobe.core.datastore.di.dataStoreModule
import org.koin.dsl.module

val dataModule =
    module {
        includes(daoModule)
        includes(dataStoreModule)
    }
