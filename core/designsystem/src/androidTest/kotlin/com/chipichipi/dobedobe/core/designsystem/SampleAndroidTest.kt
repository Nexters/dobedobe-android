package com.chipichipi.dobedobe.core.designsystem

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SampleAndroidTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun sample() {
        composeTestRule.setContent {
            Button(onClick = { /*TODO*/ }) {
                Text("Hello")
            }
        }
        composeTestRule.onNodeWithText("Hello")
            .assertIsDisplayed()
    }

    @Test
    fun compose_runtime_tes() {
        composeTestRule.setContent {
            var count by remember { mutableIntStateOf(1) }
            Button(onClick = { count++ }) {
                Text("$count")
            }
        }
    }
}
