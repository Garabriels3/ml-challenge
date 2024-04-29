package com.br.products.presentation.productdetail.udf

sealed class ProductDetailUiSideEffect {
    data class OnNavigateToBrowser(val url: String) : ProductDetailUiSideEffect()
    object OnNavigateToSearchScreen : ProductDetailUiSideEffect()
    object OnNavigateBack : ProductDetailUiSideEffect()
}