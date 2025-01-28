package com.chipichipi.dobedobe.core.data.repository

import com.chipichipi.dobedobe.core.model.DashboardPhoto
import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    fun getPhotos(): Flow<List<DashboardPhoto>>

    suspend fun upsertPhotos(photos: List<DashboardPhoto>): Result<Unit>

    suspend fun deletePhotosByIds(ids: List<Int>): Result<Unit>
}
