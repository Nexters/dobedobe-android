package com.chipichipi.dobedobe.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import com.chipichipi.dobedobe.core.model.CharacterType
import com.chipichipi.dobedobe.core.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesDataSource(
    private val preferences: DataStore<UserPreferences>,
) {
    val userData: Flow<UserData> =
        preferences.data
            .map { preferences ->
                preferences.toModel()
            }

    suspend fun completeOnBoarding() {
        try {
            preferences.updateData {
                it.copy {
                    this.isOnboardingCompleted = true
                }
            }
        } catch (ioException: IOException) {
            Log.e("UserPreferences", "Failed to update preferences", ioException)
        }
    }

    suspend fun setGoalNotificationEnabled(enabled: Boolean) {
        try {
            preferences.updateData {
                it.copy {
                    notificationSetting = notificationSetting.copy {
                        isGoalNotificationEnabled = enabled
                    }
                }
            }
        } catch (ioException: IOException) {
            Log.e("UserPreferences", "Failed to update preferences", ioException)
        }
    }

    suspend fun disableSystemNotificationDialog() {
        try {
            preferences.updateData {
                it.copy {
                    notificationSetting = notificationSetting.copy {
                        isSystemNotificationDialogDisabled = true
                    }
                }
            }
        } catch (ioException: IOException) {
            Log.e("UserPreferences", "Failed to update preferences", ioException)
        }
    }

    suspend fun saveCharacter(type: CharacterType) {
        try {
            preferences.updateData {
                it.copy {
                    character = when (type) {
                        CharacterType.Bird -> CharacterProto.BIRD
                        CharacterType.Rabbit -> CharacterProto.RABBIT
                    }
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
        isGoalNotificationEnabled = notificationSetting.isGoalNotificationEnabled,
        isSystemNotificationDialogDisabled = notificationSetting.isSystemNotificationDialogDisabled,
        characterType = when (character) {
            null,
            CharacterProto.UNRECOGNIZED,
            CharacterProto.BIRD,
            -> CharacterType.Bird
            CharacterProto.RABBIT -> CharacterType.Rabbit
        },
    )
