package com.br.products.presentation.searchproduct.udf

sealed class SearchProductUiSideEffect {
    data class OnNavigateToProductDetailsEffect(val productName: String) : SearchProductUiSideEffect()
    data class OnShowToastEffect(val message: String) : SearchProductUiSideEffect()
}