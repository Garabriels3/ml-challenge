package com.br.products.presentation.products.udf

sealed class ProductsUiSideEffect {
    data class OnNavigateToDetailEffect(val productId: String) : ProductsUiSideEffect()
    object OnBackToSearchEffect : ProductsUiSideEffect()
}