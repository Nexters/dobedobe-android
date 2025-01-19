package com.chipichipi.dobedobe.core.datastore

import androidx.datastore.core.DataStore
import com.chipichipi.dobedobe.core.model.UserData
import kotlinx.coroutines.flow.map

class UserPreferencesDataSource(
    private val preferences: DataStore<UserPreferences>,
) {
    val userData =
        preferences.data
            .map { preferences ->
                preferences.toModel()
            }
}

private fun UserPreferences.toModel() =
    UserData(
        isOnboardingRequired = isOnboardingRequired,
    )
