package com.chipichipi.dobedobe.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.chipichipi.dobedobe.core.database.entity.DashboardPhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DashboardPhotoDao {
    @Query("SELECT * FROM ${DashboardPhotoEntity.TABLE_NAME}")
    fun getPhotos(): Flow<List<DashboardPhotoEntity>>

    @Upsert
    suspend fun upsertPhotos(photos: List<DashboardPhotoEntity>)

    @Query("DELETE FROM ${DashboardPhotoEntity.TABLE_NAME} WHERE id = :id")
    suspend fun deletePhotoById(id: Int)
}
