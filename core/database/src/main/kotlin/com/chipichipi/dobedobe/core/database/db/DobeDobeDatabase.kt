package com.chipichipi.dobedobe.core.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import com.chipichipi.dobedobe.core.database.convertor.InstantConverter
import com.chipichipi.dobedobe.core.database.dao.GoalDao
import com.chipichipi.dobedobe.core.database.entity.GoalEntity

@TypeConverters(
    InstantConverter::class,
)
@Database(
    entities = [GoalEntity::class],
    version = 1,
)
internal abstract class DobeDobeDatabase : RoomDatabase() {
    abstract fun goalDao(): GoalDao

    companion object {
        const val DATABASE_NAME = "dobedobe.db"
        val MIGRATIONS: Array<Migration> = arrayOf()
    }
}
