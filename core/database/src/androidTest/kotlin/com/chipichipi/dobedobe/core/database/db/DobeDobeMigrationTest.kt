package com.chipichipi.dobedobe.core.database.db

import android.content.Context
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import java.io.IOException

class DobeDobeMigrationTest {
    @get:Rule
    val helper =
        MigrationTestHelper(
            InstrumentationRegistry.getInstrumentation(),
            DobeDobeDatabase::class.java,
        )

    @Test
    @DisplayName("모든 database 버전 마이그레이션 테스트")
    @Throws(IOException::class)
    fun test_migrateAll() {
        // given
        val dbName = "TEST_DB"
        val oldestDbVersion = 1
        val testContext = ApplicationProvider.getApplicationContext<Context>()

        // when : 가장 오래된 버전의 DB를 생성하고 닫음
        helper.createDatabase(dbName, oldestDbVersion)
            .close()

        // then : 최신 버전 DB로 마이그레이션 후 검증
        Room.databaseBuilder(
            testContext,
            DobeDobeDatabase::class.java,
            dbName,
        ).addMigrations(*DobeDobeDatabase.MIGRATIONS)
            .build().apply {
                openHelper.writableDatabase.close()
            }
    }
}