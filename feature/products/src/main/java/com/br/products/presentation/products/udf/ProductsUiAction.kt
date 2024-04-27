package com.br.products.presentation.products.udf

sealed class ProductsUiAction {
    data class OnStartScreen(val searchedTerm: String) : ProductsUiAction()
}