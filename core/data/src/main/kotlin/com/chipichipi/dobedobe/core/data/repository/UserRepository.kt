package com.chipichipi.dobedobe.core.data.repository

import com.chipichipi.dobedobe.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val userData: Flow<UserData>
}
