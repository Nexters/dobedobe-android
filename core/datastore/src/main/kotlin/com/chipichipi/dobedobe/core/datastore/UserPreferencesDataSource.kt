package com.chipichipi.dobedobe.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
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

    suspend fun setOnboardingCompleted(isOnboardingCompleted: Boolean) {
        try {
            preferences.updateData {
                it.copy {
                    this.isOnboardingCompleted = isOnboardingCompleted
                }
            }
        } catch (ioException: IOException) {
            Log.e("UserPreferences", "Failed to update preferences", ioException)
        }
    }
}

private fun UserPreferences.toModel() =
    UserData(
        isOnboardingCompleted = isOnboardingCompleted,
    )
