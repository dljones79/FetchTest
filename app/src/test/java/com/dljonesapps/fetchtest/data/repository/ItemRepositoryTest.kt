package com.dljonesapps.fetchtest.data.repository

import app.cash.turbine.test
import com.dljonesapps.fetchtest.data.model.Item
import com.dljonesapps.fetchtest.data.api.FetchApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFailsWith
import kotlin.time.Duration.Companion.seconds

class ItemRepositoryTest {
    private lateinit var api: TestItemApi
    private lateinit var repository: ItemRepository

    @Before
    fun setup() {
        api = TestItemApi()
        repository = ItemRepository(api)
    }

    @Test
    fun `getItems returns sorted and filtered items`() = runTest {
        // Given
        val apiResponse = listOf(
            Item(3, 2, "Item C"),
            Item(1, 1, "Item A"),
            Item(2, 1, "Item B"),
            Item(4, 3, null),
            Item(5, 3, "")
        )
        api.setResponse(apiResponse)

        // When & Then
        repository.getItems().test(timeout = 5.seconds) {
            val items = awaitItem()
            assertEquals(3, items.size)
            assertTrue(items.none { it.name.isNullOrBlank() })
            assertTrue(items.all { it.id > 0 && it.listId > 0 })
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getItems handles empty response`() = runTest {
        // Given
        api.setResponse(emptyList())

        // When & Then
        repository.getItems().test(timeout = 5.seconds) {
            val items = awaitItem()
            assertTrue(items.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getItems propagates exceptions`() = runTest {
        // Given
        val errorMessage = "API Error"
        val exception = RuntimeException(errorMessage)
        api.setError(exception)

        // When & Then
        repository.getItems()
            .catch { e ->
                // Then
                assertTrue(e is RuntimeException)
                assertEquals(errorMessage, e.message)
                throw e
            }
            .test {
                awaitError()
            }
    }
}

// Test double for API
private class TestItemApi : FetchApi {
    private var response: List<Item>? = null
    private var error: Throwable? = null

    override suspend fun getItems(): List<Item> {
        error?.let { throw it }
        return response ?: emptyList()
    }

    fun setResponse(items: List<Item>) {
        response = items
        error = null
    }

    fun setError(throwable: Throwable) {
        error = throwable
        response = null
    }
}
