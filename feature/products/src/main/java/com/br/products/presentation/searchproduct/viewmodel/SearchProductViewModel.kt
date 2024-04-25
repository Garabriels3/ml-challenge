package com.br.products.presentation.searchproduct.viewmodel

import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.infra.coroutines.MutableSingleLiveEvent
import com.br.products.domain.usecase.terms.GetTermsHistoryUseCase
import com.br.products.domain.usecase.terms.SaveTermsHistoryUseCase
import com.br.products.presentation.searchproduct.udf.SearchProductUiAction
import com.br.products.presentation.searchproduct.udf.SearchProductUiSideEffect
import com.br.products.presentation.searchproduct.udf.SearchProductUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SearchProductViewModel(
    private val getTermsHistoryUseCase: GetTermsHistoryUseCase,
    private val addSearchTermUseCase: SaveTermsHistoryUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<SearchProductUiState>(SearchProductUiState.OnLoadingState)
    val uiState get() = _uiState.asStateFlow()

    private val _uiSideEffect = MutableSingleLiveEvent<SearchProductUiSideEffect>()

    init {
        getSearchTerms()
    }

    fun handleAction(action: SearchProductUiAction) {
        when (action) {
            is SearchProductUiAction.OnClickProductAction -> {
                // TODO: Log event to click on product
                saveSearchTerm(action.productName)
                setNavigateToProductsSideEffect(action.productName)
            }

            is SearchProductUiAction.OnClickSearchAction -> {
                // TODO: Log event to click on search
                saveSearchTerm(action.productName)
                setNavigateToProductsSideEffect(action.productName)
            }

            is SearchProductUiAction.OnTextChangedAction -> {
                setSearchButtonState(action.productName)
                updateProductName(action.productName)
            }
        }
    }

    private fun saveSearchTerm(term: String) {
        viewModelScope.launch {
            if (term.isNotEmpty()) {
                addSearchTermUseCase(term)
            }
        }
    }

    private fun setSearchButtonState(term: String) {
        _uiState.value = SearchProductUiState.OnResumeState(
            getCurrentUiModel().copy(
                isSearchButtonEnabled = if (term.isNotEmpty()) ImeAction.Search else ImeAction.None
            )
        )
    }

    private fun getSearchTerms() {
        viewModelScope.launch {
            getTermsHistoryUseCase()
                .onStart {
                    _uiState.value = SearchProductUiState.OnLoadingState
                }
                .collect {
                    _uiState.value = SearchProductUiState.OnResumeState(
                        getCurrentUiModel().copy(
                            productsHistory = it
                        )
                    )
                }
        }
    }

    private fun updateProductName(productName: String) {
        _uiState.value = SearchProductUiState.OnResumeState(
            getCurrentUiModel().copy(
                productName = productName,
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