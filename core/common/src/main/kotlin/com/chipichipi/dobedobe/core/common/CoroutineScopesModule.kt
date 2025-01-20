package com.chipichipi.dobedobe.core.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coroutineScopesModule =
    module {
        includes(dispatchersModule)
        single<CoroutineScope> {
            CoroutineScope(SupervisorJob() + get<CoroutineDispatcher>(named("default")))
        }
    }
