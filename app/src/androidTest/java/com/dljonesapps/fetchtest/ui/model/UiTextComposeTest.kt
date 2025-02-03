package com.dljonesapps.fetchtest.ui.model

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UiTextComposeTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context
        get() = ApplicationProvider.getApplicationContext()

    @Test
    fun dynamicString_composable_returnsCorrectValue() {
        // Given
        val text = "Hello, Compose!"
        val uiText = UiText.DynamicString(text)

        // When
        composeTestRule.setContent {
            Text(text = uiText.asString())
        }

        // Then
        composeTestRule.onNodeWithText(text).assertExists()
    }

    @Test
    fun stringResource_composable_returnsCorrectValue() {
        // Given
        val resourceId = android.R.string.ok
        val expectedText = context.getString(resourceId)
        val uiText = UiText.StringResource(resourceId)

        // When
        composeTestRule.setContent {
            Text(text = uiText.asString())
        }

        // Then
        composeTestRule.onNodeWithText(expectedText).assertExists()
    }

    @Test
    fun emptyDynamicString_composable_returnsEmptyString() {
        // Given
        val uiText = UiText.DynamicString("")

        // When
        composeTestRule.setContent {
            Text(text = uiText.asString())
        }

        // Then
        composeTestRule.onNodeWithText("", useUnmergedTree = true).assertExists()
    }

    @Test
    fun dynamicString_composable_updatesWhenValueChanges() {
        // Given
        val initialText = "Initial"
        val updatedText = "Updated"
        var text by mutableStateOf(initialText)
        
        // When
        composeTestRule.setContent {
            Text(text = UiText.DynamicString(text).asString())
        }

        // Then - Initial state
        composeTestRule.onNodeWithText(initialText).assertExists()

        // When - Update state
        text = updatedText
        
        // Then - Updated state
        composeTestRule.onNodeWithText(updatedText).assertExists()
    }

    @Test
    fun stringResource_composable_handlesNonExistentResource() {
        // Given
        val nonExistentResourceId = 0 // Using 0 as it's an invalid resource ID
        val uiText = UiText.StringResource(nonExistentResourceId)

        // When
        composeTestRule.setContent {
            Text(text = uiText.asString())
        }

        // Then - Should show empty string for non-existent resource
        composeTestRule.onNodeWithText("", useUnmergedTree = true).assertExists()
    }
}
