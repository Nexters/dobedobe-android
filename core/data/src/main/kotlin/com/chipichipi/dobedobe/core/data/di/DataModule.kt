package com.chipichipi.dobedobe.core.data.di

import com.chipichipi.dobedobe.core.data.repository.UserRepository
import com.chipichipi.dobedobe.core.data.repository.UserRepositoryImpl
import com.chipichipi.dobedobe.core.database.di.daoModule
import com.chipichipi.dobedobe.core.datastore.di.dataStoreModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule =
    module {
        includes(
            daoModule,
            dataStoreModule,
        )
        singleOf(::UserRepositoryImpl) bind UserRepository::class
    }
