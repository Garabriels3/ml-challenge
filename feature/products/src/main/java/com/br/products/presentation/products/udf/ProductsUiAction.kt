package com.br.products.presentation.products.udf

sealed class ProductsUiAction {
    data class OnStartScreenAction(val searchedTerm: String) : ProductsUiAction()
    data class OnProductClickAction(val productId: String) : ProductsUiAction()
    data class OnProductsOrientationClickAction(val orientation: ProductsOrientation) : ProductsUiAction()
    object OnRetryAction : ProductsUiAction()
    object OnClickSearchBarAction : ProductsUiAction()
}