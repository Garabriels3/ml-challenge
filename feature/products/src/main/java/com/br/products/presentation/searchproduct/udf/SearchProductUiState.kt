package com.br.products.presentation.searchproduct.udf

import androidx.compose.ui.text.input.ImeAction

data class SearchProductUiModel(
    val productName: String = "",
    val productsHistory: List<String> = emptyList(),
    val errorMessage: String = "",
    val isSearchButtonEnabled: ImeAction = ImeAction.None
)

sealed class SearchProductUiState(open val uiModel: SearchProductUiModel) {
    object OnLoadingState : SearchProductUiState(SearchProductUiModel())
    data class OnResumeState(override val uiModel: SearchProductUiModel) :
        SearchProductUiState(uiModel)
    object OnEmptyState : SearchProductUiState(SearchProductUiModel())
    data class OnErrorState(override val uiModel: SearchProductUiModel) :
        SearchProductUiState(uiModel)
}