package com.br.products.presentation.searchproduct.udf

data class SearchProductUiModel(
    val productName: String = "",
    val productsHistory: List<String> = emptyList(),
    val errorMessage: String = "",
)

sealed class SearchProductUiState(open val uiModel: SearchProductUiModel) {
    object InitialState : SearchProductUiState(SearchProductUiModel())
    data class OnResumeState(override val uiModel: SearchProductUiModel) :
        SearchProductUiState(uiModel)
    object EmptyState : SearchProductUiState(SearchProductUiModel())
    data class OnErrorState(override val uiModel: SearchProductUiModel) :
        SearchProductUiState(uiModel)
}