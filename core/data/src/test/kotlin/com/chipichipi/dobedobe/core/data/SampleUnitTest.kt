package com.chipichipi.dobedobe.core.data

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.Test

class SampleUnitTest {
    @Test
    fun addition_isCorxrect() {
        4 shouldBe 2 + 2
    }

    @Test
    fun `compose_test`() = runTest {
        delay(10)
    }

    @Test
    fun `datetime_test`() {
        LocalDate(2024, 1, 13).toString() shouldBe "2024-01-13"
    }

    @Test
    fun `application_context`() {
//        val context = ApplicationProvider.getApplicationContext<Context>()
    }
}
