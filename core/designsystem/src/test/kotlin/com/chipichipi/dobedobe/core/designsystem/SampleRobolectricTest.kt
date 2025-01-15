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
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SampleRobolectricTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun compose_ui_test() {
        composeTestRule.setContent {
            Button(onClick = { /*TODO*/ }) {
                Text("Hello")
            }
        }
        composeTestRule.onNodeWithText("Hello")
            .assertIsDisplayed()
    }

    @Test
    fun compose_runtime_test() {
        composeTestRule.setContent {
            var count by remember { mutableIntStateOf(1) }
            Button(onClick = { count++ }) {
                Text("$count")
            }
        }
        composeTestRule.onNodeWithText("1")
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithText("2")
            .assertIsDisplayed()
    }
}
