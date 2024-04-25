package com.br.products.presentation.searchproduct.view.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.br.design_system.compose.toolbar.SearchBarComponent
import com.br.design_system.theme.MlChallengeTheme
import com.br.products.presentation.searchproduct.udf.SearchProductUiState

@Composable
fun SearchProductScreen(state: SearchProductUiState) {
    when (state) {
        is SearchProductUiState.OnResumeState -> {
            SearchProductContent()
        }
        is SearchProductUiState.InitialState -> {
        }
        is SearchProductUiState.EmptyState -> {
        }
        is SearchProductUiState.OnErrorState -> {
        }
    }
}

@Composable
private fun SearchProductContent() {
    Scaffold(
        topBar = {
            SearchBarComponent(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                label = "Buscar",
                placeholder = "Buscar",
                onValueChange = {},
                onSearchFieldClick = {},
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            item {
                Text(
                    text = "Search Product Screen",
                )
            }
        }
    }
}

@Preview
@Composable
private fun SearchProductScreenPreview() {
    MlChallengeTheme {
        SearchProductScreen(
            SearchProductUiState.InitialState
        )
    }
}