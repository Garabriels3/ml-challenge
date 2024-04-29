package com.br.products.presentation.productdetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.infra.coroutines.MutableSingleLiveEvent
import com.br.network.exception.GenericException
import com.br.network.exception.NetworkException
import com.br.products.domain.model.AttributeDomain
import com.br.products.domain.model.PictureDomain
import com.br.products.domain.model.ProductDetailDomain
import com.br.products.domain.usecase.product_detail.GetProductDetailUseCase
import com.br.products.presentation.model.AttributeUi
import com.br.products.presentation.model.PictureUi
import com.br.products.presentation.model.ProductDetailUi
import com.br.products.presentation.productdetail.udf.ProductDetailUiAction
import com.br.products.presentation.productdetail.udf.ProductDetailUiModel
import com.br.products.presentation.productdetail.udf.ProductDetailUiSideEffect
import com.br.products.presentation.productdetail.udf.ProductDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val getProductDetailUseCase: GetProductDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductDetailUiState>(
        ProductDetailUiState.OnLoadingState(ProductDetailUiModel())
    )
    val uiState get() = _uiState.asStateFlow()

    private val _uiSideEffect = MutableSingleLiveEvent<ProductDetailUiSideEffect>()
    val uiSideEffect get() = _uiSideEffect.asSingleEvent()

    fun handleAction(action: ProductDetailUiAction) {
        when (action) {

            is ProductDetailUiAction.OnClickTryAgain -> {
                getProductDetail(action.productId)
            }

            is ProductDetailUiAction.OnClickNavigateToSearchScreen -> {
                _uiSideEffect.emit(ProductDetailUiSideEffect.OnNavigateToSearchScreen)
            }

            is ProductDetailUiAction.OnStartScreenAction -> {
                getProductDetail(action.productId)
            }

            is ProductDetailUiAction.OnClickExpandAttributes -> {
                _uiState.value = ProductDetailUiState.OnResumedState(
                    getCurrentUiModel().copy(
                        isExpandedAttributes = !action.currentExpandAttributes
                    )
                )
            }

            is ProductDetailUiAction.OnClickNavigateToExternalBrowser -> {
                _uiSideEffect.emit(ProductDetailUiSideEffect.OnNavigateBack)
            }

            is ProductDetailUiAction.OnClickNavigateBack -> {
                _uiSideEffect.emit(ProductDetailUiSideEffect.OnNavigateBack)
            }
        }
    }

    private fun getProductDetail(productId: String) {
        viewModelScope.launch {
            getProductDetailUseCase(productId)
                .onStart {
                    _uiState.value = ProductDetailUiState.OnLoadingState(
                        getCurrentUiModel().copy(
                            productId = productId
                        )
                    )
                }
                .map {
                    it.productDetailDomainToUiModel()
                }
                .catch { it.handleError() }
                .collect {
                    _uiState.value = ProductDetailUiState.OnResumedState(
                        getCurrentUiModel().copy(
                            productDetail = it
                        )
                    )
                }
        }
    }

    private fun Throwable.handleError() {
        when (this) {
            is NetworkException -> {
                _uiState.value = ProductDetailUiState.OnNetworkState(
                    getCurrentUiModel().copy(
                        errorMessage = message
                    )
                )
            }

            is GenericException -> {
                _uiState.value = ProductDetailUiState.OnErrorState(
                    getCurrentUiModel().copy(
                        errorMessage = message
                    )
                )
            }

            else -> {
                _uiState.value = ProductDetailUiState.OnErrorState(
                    getCurrentUiModel().copy(
                        errorMessage = message
                    )
                )
            }
        }
    }

    private fun ProductDetailDomain.productDetailDomainToUiModel() = ProductDetailUi(
        id = id,
        title = title,
        pictures = pictures.pictureDomainToUi(),
        attributes = attributes.attributeDomainToUi(),
        price = priceFormatted(),
        warranty = warranty,
        acceptsMercadoPago = acceptsMercadoPago,
        freeShipping = freeShipping
    )

    private fun List<AttributeDomain>.attributeDomainToUi() = map {
        AttributeUi(
            id = it.id,
            name = it.name,
            description = it.description
        )
    }

    private fun List<PictureDomain>.pictureDomainToUi() = map {
        PictureUi(
            id = it.id,
            url = it.url
        )
    }

    private fun getCurrentUiModel() = checkNotNull(_uiState.value).uiModel
}