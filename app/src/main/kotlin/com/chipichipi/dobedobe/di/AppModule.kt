package com.chipichipi.dobedobe.di

import com.chipichipi.dobedobe.core.data.di.dataModule
import com.chipichipi.dobedobe.feature.dashboard.di.dashboardModule
import com.chipichipi.dobedobe.feature.goal.di.goalModule
import com.chipichipi.dobedobe.feature.setting.di.settingModule
import org.koin.dsl.module

val featureModule =
    module {
        includes(dashboardModule, goalModule, settingModule)
    }

val appModule =
    module {
        includes(featureModule)

        includes(dataModule, featureModule)
    }
