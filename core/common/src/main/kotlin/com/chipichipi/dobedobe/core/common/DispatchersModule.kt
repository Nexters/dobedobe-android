package com.chipichipi.dobedobe.core.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dispatchersModule =
    module {
        single<CoroutineDispatcher> { Dispatchers.IO }
        single<CoroutineDispatcher>(named("default")) { Dispatchers.Default }
    }
