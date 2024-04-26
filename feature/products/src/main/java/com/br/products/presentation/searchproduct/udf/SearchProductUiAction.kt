package com.br.products.presentation.searchproduct.udf

sealed class SearchProductUiAction {
    data class OnClickSearchAction(val productName: String, val networkAvailable: Boolean) : SearchProductUiAction()
    data class OnTextChangedAction(val productName: String) : SearchProductUiAction()
}