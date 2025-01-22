package com.chipichipi.dobedobe.core.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.chipichipi.dobedobe.core.database.db.DobeDobeDatabase
import org.koin.dsl.module

internal val databaseModule =
    module {
        single<RoomDatabase> {
            Room.databaseBuilder(
                get(),
                DobeDobeDatabase::class.java,
                DobeDobeDatabase.DATABASE_NAME,
            ).fallbackToDestructiveMigration() // TODO: 최초 배포 시 삭제
                .build()
        }
    }
