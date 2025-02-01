package com.chipichipi.dobedobe.core.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import com.chipichipi.dobedobe.core.database.convertor.InstantConverter
import com.chipichipi.dobedobe.core.database.dao.DashboardPhotoDao
import com.chipichipi.dobedobe.core.database.dao.GoalDao
import com.chipichipi.dobedobe.core.database.entity.DashboardPhotoEntity
import com.chipichipi.dobedobe.core.database.entity.GoalEntity

@Database(
    entities = [
        GoalEntity::class,
        DashboardPhotoEntity::class,
    ],
    version = 1,
)
@TypeConverters(
    InstantConverter::class,
)
internal abstract class DobeDobeDatabase : RoomDatabase() {
    abstract fun goalDao(): GoalDao

    abstract fun dashboardPhotoDao(): DashboardPhotoDao

    companion object {
        const val DATABASE_NAME = "dobedobe.db"
        val MIGRATIONS: Array<Migration> = arrayOf()
    }
}
