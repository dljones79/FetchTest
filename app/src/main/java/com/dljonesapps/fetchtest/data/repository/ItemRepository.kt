package com.dljonesapps.fetchtest.data.repository

import com.dljonesapps.fetchtest.data.api.FetchApi
import com.dljonesapps.fetchtest.data.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ItemRepository(private val api: FetchApi) {
    fun getItems(): Flow<List<Item>> = flow {
        try {
            val items = api.getItems()
                .filter { !it.name.isNullOrBlank() }
                .groupBy { it.listId }
                .mapValues { (_, items) ->
                    items.sortedBy { it.name }
                }
                .toSortedMap()
                .values
                .flatten()
            
            emit(items)
        } catch (e: Exception) {
            throw e
        }
    }
}
