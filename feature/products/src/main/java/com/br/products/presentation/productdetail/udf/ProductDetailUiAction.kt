package com.br.products.presentation.productdetail.udf

sealed class ProductDetailUiAction {
    data class OnClickNavigateToExternalBrowser(val url: String) : ProductDetailUiAction()
    object OnClickNavigateToSearchScreen : ProductDetailUiAction()
    data class OnClickTryAgain(val productId: String) : ProductDetailUiAction()
    data class OnClickExpandAttributes(val currentExpandAttributes: Boolean) : ProductDetailUiAction()
    data class OnStartScreenAction(val productId: String) : ProductDetailUiAction()
    object OnClickNavigateBack : ProductDetailUiAction()
}