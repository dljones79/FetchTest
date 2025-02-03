package com.dljonesapps.fetchtest.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dljonesapps.fetchtest.R
import com.dljonesapps.fetchtest.data.model.Item
import com.dljonesapps.fetchtest.ui.components.ItemCard
import com.dljonesapps.fetchtest.ui.model.UiText
import com.dljonesapps.fetchtest.ui.viewmodel.ItemUiState

@Composable
fun ItemScreen(
    uiState: ItemUiState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is ItemUiState.Loading -> LoadingScreen(modifier)
        is ItemUiState.Error -> ErrorScreen(uiState.message, modifier, onRetry)
        is ItemUiState.Success -> ItemList(items = uiState.items, modifier)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp
            )
        }
    }
}

@Composable
fun ErrorScreen(
    message: UiText,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message.asString(),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text(stringResource(R.string.button_retry))
        }
    }
}

@Composable
fun ItemList(
    items: Map<Int, List<Item>>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { (listId, itemList) ->
            item {
                Text(
                    text = stringResource(R.string.list_title_format, listId),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            items(itemList) { item ->
                ItemCard(item = item)
            }
        }
    }
}
