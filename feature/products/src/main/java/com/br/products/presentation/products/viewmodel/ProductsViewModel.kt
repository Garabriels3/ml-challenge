package com.br.products.presentation.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.br.infra.coroutines.MutableSingleLiveEvent
import com.br.products.R
import com.br.products.domain.model.ProductItemDomain
import com.br.products.domain.usecase.products.GetProductsUseCase
import com.br.products.presentation.products.udf.ProductUi
import com.br.products.presentation.products.udf.ProductsUiAction
import com.br.products.presentation.products.udf.ProductsUiSideEffect
import com.br.products.presentation.products.udf.ProductsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductsUiState>(ProductsUiState.OnLoadingState)
    val uiState get() = _uiState.asStateFlow()

    private val _uiSideEffect = MutableSingleLiveEvent<ProductsUiSideEffect>()
    val uiSideEffect get() = _uiSideEffect.asSingleEvent()

    fun handleAction(action: ProductsUiAction) {
        when (action) {
            is ProductsUiAction.OnStartScreen -> {
                getProducts(action.searchedTerm)
            }
        }
    }

    private fun getProducts(searchedTerm: String) {
        viewModelScope.launch {
            getProductsUseCase(searchedTerm)
                .cachedIn(this)
                .onStart { _uiState.value = ProductsUiState.OnLoadingState }
                .map { pages ->
                    pages.map { it.toProductUi() }
                }
                .collectLatest {
                    _uiState.value = ProductsUiState.OnResumedGridState(
                        getCurrentUiModel().copy(
                            products = flowOf(it)
                        )
                    )
                }
        }
    }

    private fun ProductItemDomain.toProductUi() = ProductUi(
        id = id,
        name = title,
        price = price,
        imageUrl = thumbnail,
        condition = condition,
        availableQuantity = availableQuantity,
        freeShipping = setFreeShippingText(freeShipping)
    )

    private fun setFreeShippingText(freeShipping: Boolean) =
        if (freeShipping) R.string.products_free_shipping_label else null

    private fun getCurrentUiModel() = checkNotNull(_uiState.value).uiModel
}