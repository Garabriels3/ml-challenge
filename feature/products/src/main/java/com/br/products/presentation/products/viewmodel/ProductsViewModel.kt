package com.br.products.presentation.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.br.infra.coroutines.MutableSingleLiveEvent
import com.br.network.exception.GenericException
import com.br.network.exception.NetworkException
import com.br.products.R
import com.br.products.domain.model.ProductItemDomain
import com.br.products.domain.usecase.products.GetProductsUseCase
import com.br.products.presentation.model.ProductUi
import com.br.products.presentation.products.udf.ProductsOrientation
import com.br.products.presentation.products.udf.ProductsUiAction
import com.br.products.presentation.products.udf.ProductsUiModel
import com.br.products.presentation.products.udf.ProductsUiSideEffect
import com.br.products.presentation.products.udf.ProductsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductsUiState>(
        ProductsUiState.OnLoadingState(
            ProductsUiModel()
        )
    )
    val uiState get() = _uiState.asStateFlow()

    private val _uiSideEffect = MutableSingleLiveEvent<ProductsUiSideEffect>()
    val uiSideEffect get() = _uiSideEffect.asSingleEvent()

    fun handleAction(action: ProductsUiAction) {
        when (action) {
            is ProductsUiAction.OnStartScreenAction -> {
                getProducts(action.searchedTerm)
            }

            is ProductsUiAction.OnRetryAction -> {
                getProducts(getCurrentUiModel().searchedTerm)
            }

            is ProductsUiAction.OnClickSearchBarAction -> {
                _uiSideEffect.emit(ProductsUiSideEffect.OnBackToSearchEffect)
            }

            is ProductsUiAction.OnProductClickAction -> {
                _uiSideEffect.emit(ProductsUiSideEffect.OnNavigateToDetailEffect(action.productId))
            }
            is ProductsUiAction.OnProductsOrientationClickAction -> {
                if (action.orientation == ProductsOrientation.GRID) {
                    _uiState.value = ProductsUiState.OnResumedGridState(
                        getCurrentUiModel().copy(
                            productsOrientation = ProductsOrientation.LIST
                        )
                    )
                } else {
                    _uiState.value = ProductsUiState.OnResumedListState(
                        getCurrentUiModel().copy(
                            productsOrientation = ProductsOrientation.GRID
                        )
                    )
                }
            }
        }
    }

    private fun getProducts(searchedTerm: String) {
        viewModelScope.launch {
            getProductsUseCase(searchedTerm)
                .onStart {
                    _uiState.value = ProductsUiState.OnLoadingState(
                        getCurrentUiModel().copy(
                            searchedTerm = searchedTerm
                        )
                    )
                }
                .catch { throwable ->
                    throwable.handleError()
                }
                .map { pages ->
                    pages.map {
                        it.toProductUi()
                    }
                }
                .cachedIn(viewModelScope)
                .collect {
                    _uiState.value = ProductsUiState.OnResumedGridState(
                        getCurrentUiModel().copy(
                            products = flowOf(it),
                            searchedTerm = getCurrentUiModel().searchedTerm,
                        )
                    )
                }

        }
    }

    private fun Throwable.handleError() {
        when (this) {
            is GenericException -> {
                _uiState.value = ProductsUiState.OnErrorState(
                    getCurrentUiModel().copy(
                        error = message
                    )
                )
            }

            is NetworkException -> {
                _uiState.value = ProductsUiState.OnNetworkErrorState(
                    getCurrentUiModel().copy(
                        error = message
                    )
                )
            }

            else -> {
                _uiState.value = ProductsUiState.OnErrorState(
                    getCurrentUiModel().copy(
                        error = "Unknown error"
                    )
                )
            }
        }
    }

    private fun ProductItemDomain.toProductUi() = ProductUi(
        id = id,
        name = title,
        price = priceFormatted(),
        imageUrl = thumbnail,
        condition = condition,
        availableQuantity = availableQuantity,
        freeShipping = setFreeShippingText(freeShipping),
    )

    private fun setFreeShippingText(freeShipping: Boolean) =
        if (freeShipping) R.string.products_free_shipping_label else null

    private fun getCurrentUiModel() = checkNotNull(_uiState.value).uiModel
}