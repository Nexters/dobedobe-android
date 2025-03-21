package com.chipichipi.dobedobe

import android.app.Application
import com.chipichipi.dobedobe.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androix.startup.KoinStartup
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.logger.Level
import org.koin.dsl.koinConfiguration

@OptIn(KoinExperimentalAPI::class)
class DobeDobeApplication : Application(), KoinStartup {
    override fun onKoinStartup() =
        koinConfiguration {
            androidLogger(level = Level.DEBUG)
            androidContext(applicationContext)
            modules(appModule)
        }
}
