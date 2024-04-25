package com.br.products.presentation.searchproduct.viewmodel

import androidx.lifecycle.ViewModel
import com.br.infra.coroutines.MutableSingleLiveEvent
import com.br.products.presentation.searchproduct.udf.SearchProductUiAction
import com.br.products.presentation.searchproduct.udf.SearchProductUiSideEffect
import com.br.products.presentation.searchproduct.udf.SearchProductUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchProductViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<SearchProductUiState>(SearchProductUiState.InitialState)
    val uiState get() = _uiState.asStateFlow()

    private val _uiSideEffect = MutableSingleLiveEvent<SearchProductUiSideEffect>()

    fun handleAction(action: SearchProductUiAction) {
        when (action) {
            is SearchProductUiAction.OnClickProductAction -> {
                // TODO: Log event to click on product
                setNavigateToProductsSideEffect(action.productName)
            }

            is SearchProductUiAction.OnClickSearchAction -> {
                // TODO: Log event to click on search
                setNavigateToProductsSideEffect(action.productName)
            }

            is SearchProductUiAction.OnTextChangedAction -> {
                filterProductsHistory(action.productName)
            }
        }
    }

    private fun filterProductsHistory(productName: String) {
        val productsHistory = getCurrentUiModel().productsHistory
        val filteredProductsHistory = productsHistory.filter { it.contains(productName) }
        _uiState.value = SearchProductUiState.OnResumeState(
            getCurrentUiModel().copy(
                productsHistory = filteredProductsHistory
            )
        )
    }

    private fun setNavigateToProductsSideEffect(productName: String) {
        _uiSideEffect.emit(
            SearchProductUiSideEffect.OnNavigateToProductDetailsEffect(
                productName
            )
        )
    }

    private fun getCurrentUiModel() = checkNotNull(_uiState.value).uiModel
}