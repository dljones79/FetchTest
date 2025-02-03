package com.dljonesapps.fetchtest.ui.model

import android.content.Context
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class UiTextTest {
    private val context: Context = mockk()

    @Test
    fun `DynamicString returns correct value from context`() {
        // Given
        val text = "Hello, World!"
        val uiText = UiText.DynamicString(text)

        // When & Then
        assertEquals(text, uiText.asString(context))
    }

    @Test
    fun `StringResource returns correct value from context`() {
        // Given
        val resourceId = 123
        val expectedText = "Hello, Resource!"
        every { context.getString(resourceId) } returns expectedText
        val uiText = UiText.StringResource(resourceId)

        // When & Then
        assertEquals(expectedText, uiText.asString(context))
    }

    @Test
    fun `DynamicString equals works correctly`() {
        // Given
        val text = "Test"
        val uiText1 = UiText.DynamicString(text)
        val uiText2 = UiText.DynamicString(text)
        val uiText3 = UiText.DynamicString("Different")

        // Then
        assertEquals<UiText>(uiText1, uiText2)
        assertEquals(uiText1.hashCode(), uiText2.hashCode())
        assertNotEquals<UiText>(uiText1, uiText3)
    }

    @Test
    fun `StringResource equals works correctly`() {
        // Given
        val resourceId = 123
        val uiText1 = UiText.StringResource(resourceId)
        val uiText2 = UiText.StringResource(resourceId)
        val uiText3 = UiText.StringResource(456)

        // Then
        assertEquals<UiText>(uiText1, uiText2)
        assertEquals(uiText1.hashCode(), uiText2.hashCode())
        assertNotEquals<UiText>(uiText1, uiText3)
    }

    @Test
    fun `Different UiText types are not equal`() {
        // Given
        val uiText1 = UiText.DynamicString("123")
        val uiText2 = UiText.StringResource(123)

        // Then
        assertNotEquals<UiText>(uiText1, uiText2)
    }

    @Test
    fun `DynamicString with empty string works correctly`() {
        // Given
        val uiText = UiText.DynamicString("")

        // When & Then
        assertEquals("", uiText.asString(context))
    }

    @Test
    fun `StringResource with null context returns empty string`() {
        // Given
        val uiText = UiText.StringResource(123)

        // When & Then
        assertEquals("", uiText.asString(null))
    }
}
