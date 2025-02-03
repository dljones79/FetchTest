package com.dljonesapps.fetchtest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dljonesapps.fetchtest.R
import com.dljonesapps.fetchtest.data.model.Item
import com.dljonesapps.fetchtest.data.repository.ItemRepository
import com.dljonesapps.fetchtest.ui.model.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ItemUiState>(ItemUiState.Loading)
    val uiState: StateFlow<ItemUiState> = _uiState.asStateFlow()

    init {
        loadItems()
    }

    fun loadItems() {
        viewModelScope.launch {
            _uiState.value = ItemUiState.Loading
            try {
                repository.getItems().collect { items ->
                    val filteredItems = items
                        .filter { !it.name.isNullOrBlank() }
                        .sortedBy { it.listId }
                        .groupBy { it.listId }
                    _uiState.value = ItemUiState.Success(filteredItems)
                }
            } catch (e: Exception) {
                _uiState.value = ItemUiState.Error(
                    e.message?.let { UiText.DynamicString(it) }
                        ?: UiText.StringResource(R.string.error_generic)
                )
            }
        }
    }
}

sealed interface ItemUiState {
    data object Loading : ItemUiState
    data class Error(val message: UiText) : ItemUiState
    data class Success(val items: Map<Int, List<Item>>) : ItemUiState
}
