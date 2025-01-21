package com.chipichipi.dobedobe.core.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

val dispatchersModule =
    module {
        single<CoroutineDispatcher>(qualifier(DobeDobeDispatchers.IO)) { Dispatchers.IO }
        single<CoroutineDispatcher>(qualifier(DobeDobeDispatchers.Default)) { Dispatchers.Default }
    }
