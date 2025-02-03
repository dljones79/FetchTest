package com.dljonesapps.fetchtest.ui.viewmodel

import app.cash.turbine.test
import com.dljonesapps.fetchtest.R
import com.dljonesapps.fetchtest.data.model.Item
import com.dljonesapps.fetchtest.data.repository.ItemRepository
import com.dljonesapps.fetchtest.ui.model.UiText
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
class ItemViewModelTest {

    private lateinit var repository: ItemRepository
    private lateinit var viewModel: ItemViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadItems success with valid items should update state correctly`() = runTest {
        // Given
        val items = listOf(
            Item(1, 1, "Item 1"),
            Item(2, 1, "Item 2"),
            Item(3, 2, "Item 3")
        )
        coEvery { repository.getItems() } returns flowOf(items)
        viewModel = ItemViewModel(repository)

        // When & Then
        viewModel.uiState.test(timeout = 5.seconds) {
            assertEquals(ItemUiState.Loading, awaitItem())
            testDispatcher.scheduler.advanceUntilIdle()
            val successState = awaitItem() as ItemUiState.Success
            assertEquals(2, successState.items.size) // Two different listIds
            assertTrue(successState.items[1]?.size == 2) // Two items in list 1
            assertTrue(successState.items[2]?.size == 1) // One item in list 2
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadItems with null or blank names should filter them out`() = runTest {
        // Given
        val items = listOf(
            Item(1, 1, "Item 1"),
            Item(2, 1, null),
            Item(3, 1, ""),
            Item(4, 1, "  ")
        )
        coEvery { repository.getItems() } returns flowOf(items)
        viewModel = ItemViewModel(repository)

        // When & Then
        viewModel.uiState.test(timeout = 5.seconds) {
            assertEquals(ItemUiState.Loading, awaitItem())
            testDispatcher.scheduler.advanceUntilIdle()
            val successState = awaitItem() as ItemUiState.Success
            assertEquals(1, successState.items[1]?.size) // Only one valid item
            assertEquals("Item 1", successState.items[1]?.first()?.name)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadItems error should update state with error message`() = runTest {
        // Given
        val errorMessage = "Network error"
        coEvery { repository.getItems() } throws Exception(errorMessage)
        viewModel = ItemViewModel(repository)

        // When & Then
        viewModel.uiState.test(timeout = 5.seconds) {
            assertEquals(ItemUiState.Loading, awaitItem())
            testDispatcher.scheduler.advanceUntilIdle()
            val errorState = awaitItem() as ItemUiState.Error
            assertEquals(UiText.DynamicString(errorMessage), errorState.message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadItems error with null message should use generic error`() = runTest {
        // Given
        coEvery { repository.getItems() } throws Exception()
        viewModel = ItemViewModel(repository)

        // When & Then
        viewModel.uiState.test(timeout = 5.seconds) {
            assertEquals(ItemUiState.Loading, awaitItem())
            testDispatcher.scheduler.advanceUntilIdle()
            val errorState = awaitItem() as ItemUiState.Error
            assertEquals(UiText.StringResource(R.string.error_generic), errorState.message)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
