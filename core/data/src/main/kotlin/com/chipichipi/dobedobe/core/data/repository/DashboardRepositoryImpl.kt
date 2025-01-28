package com.chipichipi.dobedobe.core.data.repository

import com.chipichipi.dobedobe.core.database.dao.DashboardPhotoDao
import com.chipichipi.dobedobe.core.database.entity.toEntity
import com.chipichipi.dobedobe.core.database.entity.toModel
import com.chipichipi.dobedobe.core.model.DashboardPhoto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DashboardRepositoryImpl(
    private val dashboardPhotoDao: DashboardPhotoDao,
) : DashboardRepository {
    override fun getPhotos(): Flow<List<DashboardPhoto>> {
        return dashboardPhotoDao
            .getPhotos()
            .map { entities ->
                entities.map { it.toModel() }
            }
    }

    override suspend fun savePhotos(photos: List<DashboardPhoto>): Result<Unit> {
        val entities = photos.map { it.toEntity() }

        return runCatching {
            dashboardPhotoDao.upsertPhotos(entities)
        }
    }

    override suspend fun deletePhotoById(id: Int): Result<Unit> {
        return runCatching {
            dashboardPhotoDao.deletePhotoById(id)
        }
    }
}
