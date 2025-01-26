package com.chipichipi.dobedobe.core.data.repository

import com.chipichipi.dobedobe.core.datastore.UserPreferencesDataSource
import com.chipichipi.dobedobe.core.model.UserData
import kotlinx.coroutines.flow.Flow

internal class UserRepositoryImpl(
    private val userPreferencesDataSource: UserPreferencesDataSource,
) : UserRepository {
    override val userData: Flow<UserData> =
        userPreferencesDataSource.userData

    override suspend fun completeOnBoarding(): Result<Unit> {
        return runCatching {
            userPreferencesDataSource.completeOnBoarding()
        }
    }

    override suspend fun setGoalNotificationChecked(checked: Boolean): Result<Unit> {
        return runCatching {
            userPreferencesDataSource.setGoalNotificationChecked(checked)
        }
    }

    override suspend fun disableSystemNotificationDialog(): Result<Unit> {
        return runCatching {
            userPreferencesDataSource.disableSystemNotificationDialog()
        }
    }
}
