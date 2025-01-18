package com.chipichipi.dobedobe

import android.app.Application
import com.chipichipi.dobedobe.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class DobeDobeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(applicationContext)
            modules(appModule)
        }
    }
}
