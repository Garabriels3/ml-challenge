package com.br.products.presentation.searchproduct.udf

sealed class SearchProductUiSideEffect {
    data class OnNavigateToProductDetailsEffect(val productName: String) : SearchProductUiSideEffect()
    object OnShowToastEffect : SearchProductUiSideEffect()
}