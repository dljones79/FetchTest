package com.dljonesapps.fetchtest.data.model

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ItemTest {
    @Test
    fun `Item constructor sets properties correctly`() {
        // Given
        val id = 1
        val listId = 2
        val name = "Test Item"

        // When
        val item = Item(id, listId, name)

        // Then
        assertEquals(id, item.id)
        assertEquals(listId, item.listId)
        assertEquals(name, item.name)
    }

    @Test
    fun `Item allows null name`() {
        // Given & When
        val item = Item(1, 1, null)

        // Then
        assertNull(item.name)
    }

    @Test
    fun `Item equals works correctly`() {
        // Given
        val item1 = Item(1, 1, "Test")
        val item2 = Item(1, 1, "Test")
        val item3 = Item(2, 1, "Test")

        // Then
        assertEquals(item1, item2)
        assertEquals(item1.hashCode(), item2.hashCode())
        assert(item1 != item3)
    }
}
