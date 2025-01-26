package com.chipichipi.dobedobe.core.data.repository

import com.chipichipi.dobedobe.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val userData: Flow<UserData>

    suspend fun completeOnBoarding(): Result<Unit>

    suspend fun setGoalNotificationChecked(checked: Boolean): Result<Unit>

    suspend fun disableSystemNotificationDialog(): Result<Unit>
}
