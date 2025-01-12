package com.chipichipi.dobedobe.core.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.shouldBe
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        val appContext: Context = ApplicationProvider.getApplicationContext<Context>()
        appContext.packageName shouldBe "com.chipichipi.dobedobe.core.data.test"
    }
}
