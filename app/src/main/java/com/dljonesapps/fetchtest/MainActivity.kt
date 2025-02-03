package com.dljonesapps.fetchtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dljonesapps.fetchtest.ui.screens.ItemScreen
import com.dljonesapps.fetchtest.ui.theme.FetchTestTheme
import com.dljonesapps.fetchtest.ui.viewmodel.ItemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: ItemViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.uiState.collectAsState()

            FetchTestTheme(
                dynamicColor = false
            ) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(R.string.screen_title)) },
                            colors = TopAppBarDefaults.topAppBarColors()
                        )
                    }
                ) { innerPadding ->
                    ItemScreen(
                        uiState = uiState,
                        onRetry = viewModel::loadItems,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
