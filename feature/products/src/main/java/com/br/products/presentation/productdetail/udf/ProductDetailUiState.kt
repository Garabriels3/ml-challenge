package com.br.products.presentation.productdetail.udf

import com.br.products.presentation.model.ProductDetailUi

data class ProductDetailUiModel(
    val productDetail: ProductDetailUi = ProductDetailUi(),
    val productId: String = "",
    val errorMessage: String? = null
)

sealed class ProductDetailUiState(open val uiModel: ProductDetailUiModel) {
    data class OnLoadingState(override val uiModel: ProductDetailUiModel) : ProductDetailUiState(uiModel)
    data class OnResumedState(override val uiModel: ProductDetailUiModel) :
        ProductDetailUiState(uiModel)

    data class OnErrorState(override val uiModel: ProductDetailUiModel) :
        ProductDetailUiState(uiModel)

    data class OnNetworkState(override val uiModel: ProductDetailUiModel) :
        ProductDetailUiState(uiModel)
}