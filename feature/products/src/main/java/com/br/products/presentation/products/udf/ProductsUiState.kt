package com.br.products.presentation.products.udf

import androidx.paging.PagingData
import com.br.products.presentation.model.ProductUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class ProductsUiModel(
    val error: String? = null,
    val searchedTerm: String = "",
    val totalProducts: Int = 0,
    val products: Flow<PagingData<ProductUi>> = flowOf(PagingData.empty()),
)

sealed class ProductsUiState(open val uiModel: ProductsUiModel) {
    data class OnLoadingState(override val uiModel: ProductsUiModel) : ProductsUiState(uiModel)
    data class OnResumedGridState(override val uiModel: ProductsUiModel) : ProductsUiState(uiModel)
    data class OnResumedListState(override val uiModel: ProductsUiModel) : ProductsUiState(uiModel)
    data class OnErrorState(override val uiModel: ProductsUiModel) : ProductsUiState(uiModel)
    data class OnNetworkState(override val uiModel: ProductsUiModel) : ProductsUiState(uiModel)
}