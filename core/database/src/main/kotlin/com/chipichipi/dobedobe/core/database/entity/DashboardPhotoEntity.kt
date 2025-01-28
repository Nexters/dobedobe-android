package com.chipichipi.dobedobe.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chipichipi.dobedobe.core.database.entity.DashboardPhotoEntity.Companion.TABLE_NAME
import com.chipichipi.dobedobe.core.model.DashboardPhoto

@Entity(tableName = TABLE_NAME)
data class DashboardPhotoEntity(
    @PrimaryKey
    val id: Int,
    val url: String,
) {
    companion object {
        const val TABLE_NAME = "dashboard_photo"
    }
}

fun DashboardPhotoEntity.toModel(): DashboardPhoto {
    return DashboardPhoto(
        id = id,
        url = url,
    )
}

fun DashboardPhoto.toEntity(): DashboardPhotoEntity {
    return DashboardPhotoEntity(
        id = id,
        url = url,
    )
}
