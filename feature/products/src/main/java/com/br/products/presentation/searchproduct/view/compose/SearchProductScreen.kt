package com.br.products.presentation.searchproduct.view.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.br.design_system.compose.toolbar.SearchBarComponent
import com.br.design_system.theme.MlChallengeTheme
import com.br.products.presentation.searchproduct.udf.SearchProductUiAction
import com.br.products.presentation.searchproduct.udf.SearchProductUiModel
import com.br.products.presentation.searchproduct.udf.SearchProductUiState

@Composable
fun SearchProductScreen(
    state: SearchProductUiState,
    triggerAction: (SearchProductUiAction) -> Unit
) {
    when (state) {
        is SearchProductUiState.OnResumeState -> {
            SearchProductContent(
                state.uiModel,
                triggerAction
            )
        }

        is SearchProductUiState.OnLoadingState -> {
        }

        is SearchProductUiState.OnEmptyState -> {
        }

        is SearchProductUiState.OnErrorState -> {
        }
    }
}

@Composable
private fun SearchProductContent(
    uiModel: SearchProductUiModel,
    triggerAction: (SearchProductUiAction) -> Unit
) {
    Scaffold(
        topBar = {
            SearchBarComponent(
                modifier = Modifier.fillMaxWidth(),
                value = uiModel.productName,
                label = "Buscar",
                placeholder = "Buscar",
                onValueChange = {
                    triggerAction(SearchProductUiAction.OnTextChangedAction(it))
                },
                searchButtonState = uiModel.isSearchButtonEnabled,
                onSearchFieldClick = {},
                onClickSearchKeyboard = {
                    triggerAction(SearchProductUiAction.OnClickSearchAction(it))
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            items(uiModel.productsHistory) { product ->
                SearchProductItemComponent(product)
            }
        }
    }
}

@Preview
@Composable
private fun SearchProductScreenPreview() {
    MlChallengeTheme {
        SearchProductScreen(
            SearchProductUiState.OnLoadingState
        ) { }
    }
}