package com.br.products.presentation.searchproduct.udf

sealed class SearchProductUiAction {
    data class OnClickSearchAction(val productName: String) : SearchProductUiAction()
    data class OnClickProductAction(val productName: String) : SearchProductUiAction()
    data class OnTextChangedAction(val productName: String) : SearchProductUiAction()
}