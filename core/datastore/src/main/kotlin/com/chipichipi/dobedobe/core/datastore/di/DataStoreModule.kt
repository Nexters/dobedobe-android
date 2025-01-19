package com.chipichipi.dobedobe.core.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.chipichipi.dobedobe.core.datastore.UserPreferences
import com.chipichipi.dobedobe.core.datastore.UserPreferencesDataSource
import com.chipichipi.dobedobe.core.datastore.UserPreferencesSerializer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataStoreModule =
    module {
        single { UserPreferencesSerializer() }
        singleOf(::UserPreferencesDataSource)
        single<DataStore<UserPreferences>> {
            DataStoreFactory.create(
                serializer = get<UserPreferencesSerializer>(),
                scope = CoroutineScope(get<CoroutineScope>().coroutineContext + get<CoroutineDispatcher>()),
                migrations = listOf(),
            ) {
                androidContext().dataStoreFile("user_preferences.pb")
            }
        }
    }
