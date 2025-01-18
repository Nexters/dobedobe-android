package com.chipichipi.dobedobe.feature.setting.di

import com.chipichipi.dobedobe.feature.setting.SettingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingModule = module {
    viewModelOf(::SettingViewModel)
}