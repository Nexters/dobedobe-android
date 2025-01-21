package com.chipichipi.dobedobe.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.chipichipi.dobedobe.core.common.DobeDobeDispatchers
import com.chipichipi.dobedobe.core.datastore.UserPreferences
import com.chipichipi.dobedobe.core.datastore.UserPreferencesDataSource
import com.chipichipi.dobedobe.core.datastore.UserPreferencesSerializer
import kotlinx.coroutines.CoroutineScope
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

val dataStoreModule =
    module {
        single { UserPreferencesSerializer() }
        singleOf(::UserPreferencesDataSource)
        single<DataStore<UserPreferences>> {
            DataStoreFactory.create(
                serializer = get<UserPreferencesSerializer>(),
                scope =
                    CoroutineScope(
                        get<CoroutineScope>().coroutineContext +
                            get(qualifier(DobeDobeDispatchers.IO)),
                    ),
                migrations = listOf(),
            ) {
                get<Context>().dataStoreFile("user_preferences.pb")
            }
        }
    }
