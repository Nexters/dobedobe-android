package com.chipichipi.dobedobe.core.data.repository

import com.chipichipi.dobedobe.core.model.DashboardPhoto
import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    fun getPhotos(): Flow<List<DashboardPhoto>>

    suspend fun savePhotos(photos: List<DashboardPhoto>): Result<Unit>

    suspend fun deletePhotoById(id: Int): Result<Unit>
}
