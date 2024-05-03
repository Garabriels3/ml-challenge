package com.br.products.presentation.productsdetails.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.br.network.exception.NetworkException
import com.br.products.domain.model.PictureDomain
import com.br.products.domain.model.ProductDetailDomain
import com.br.products.domain.usecase.product_detail.GetProductDetailUseCase
import com.br.products.presentation.model.PictureUi
import com.br.products.presentation.model.ProductDetailUi
import com.br.products.presentation.productdetail.udf.ProductDetailUiAction
import com.br.products.presentation.productdetail.udf.ProductDetailUiModel
import com.br.products.presentation.productdetail.udf.ProductDetailUiSideEffect
import com.br.products.presentation.productdetail.udf.ProductDetailUiState
import com.br.products.presentation.productdetail.viewmodel.ProductDetailViewModel
import com.br.products.testrules.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verifyOrder
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.rules.TestRule
import kotlin.test.Test

class ProductDetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val getProductDetailUseCase = mockk<GetProductDetailUseCase>()

    private val viewModel = ProductDetailViewModel(getProductDetailUseCase)

    private val stateObserver = mockk<Observer<ProductDetailUiState>>(relaxed = true)
    private val sideEffectObserver = mockk<Observer<ProductDetailUiSideEffect>>(relaxed = true)

    @Test
    fun `OnStartScreenAction with Success should set States in correct order`() = runTest {
        // Given
        val productId = "123"
        val expectedProductDetail = productDetailUiMock()
        val expectedUiModel = productDetailUiModelMock(productId, expectedProductDetail)
        coEvery { getProductDetailUseCase(productId) } returns flowOf(productDetailDomainMock())

        // When
        viewModel.uiState.asLiveData().observeForever(stateObserver)
        viewModel.handleAction(ProductDetailUiAction.OnStartScreenAction(productId))

        // Then
        verifyOrder {
            stateObserver.onChanged(
                ProductDetailUiState.OnLoadingState(
                    ProductDetailUiModel()
                )
            )
            stateObserver.onChanged(
                ProductDetailUiState.OnResumedState(
                    expectedUiModel
                )
            )
        }
    }

    @Test
    fun `OnStartScreenAction with Network Error should set Network State Error`() = runTest {
        // Given
        val productId = "123"
        val expectedErrorMessage = "No connection"
        coEvery { getProductDetailUseCase(productId) } returns flow {
            throw NetworkException("No connection")
        }

        // When
        viewModel.uiState.asLiveData().observeForever(stateObserver)
        viewModel.handleAction(ProductDetailUiAction.OnStartScreenAction(productId))

        // Then
        verifyOrder {
            stateObserver.onChanged(
                ProductDetailUiState.OnLoadingState(
                    ProductDetailUiModel()
                )
            )
            stateObserver.onChanged(
                ProductDetailUiState.OnLoadingState(
                    productDetailUiModelMock(productId, ProductDetailUi())
                )
            )
            stateObserver.onChanged(
                ProductDetailUiState.OnNetworkErrorState(
                    ProductDetailUiModel(errorMessage = expectedErrorMessage, productId = productId)
                )
            )
        }
    }

    @Test
    fun `OnStartScreenAction with Generic Error should set Generic State Error`() = runTest {
        // Given
        val productId = "123"
        val expectedErrorMessage = "Unknown error"
        coEvery { getProductDetailUseCase(productId) } returns flow {
            throw Exception("Unknown error")
        }

        // When
        viewModel.uiState.asLiveData().observeForever(stateObserver)
        viewModel.handleAction(ProductDetailUiAction.OnStartScreenAction(productId))

        // Then
        val actualError =
            (viewModel.uiState.value as? ProductDetailUiState.OnErrorState)?.uiModel?.errorMessage
        assertEquals(expectedErrorMessage, actualError)
    }

    @Test
    fun `OnClickNavigateBack should emit OnNavigateBack`() {
        // Given
        viewModel.uiSideEffect.observeForever(sideEffectObserver)

        // When
        viewModel.handleAction(ProductDetailUiAction.OnClickNavigateBack)

        // Then
        verifyOrder {
            sideEffectObserver.onChanged(ProductDetailUiSideEffect.OnNavigateBack)
        }
    }

    @Test
    fun `OnClickNavigateToProductDetail should emit OnNavigateToSearchScreen`() {
        // Given
        viewModel.uiSideEffect.observeForever(sideEffectObserver)

        // When
        viewModel.handleAction(ProductDetailUiAction.OnClickNavigateToSearchScreen)

        // Then
        verifyOrder {
            sideEffectObserver.onChanged(ProductDetailUiSideEffect.OnNavigateToSearchScreen)
        }
    }

    @Test
    fun `OnClickToBuy should emit OnNavigateToBrowser`() {
        // Given
        val url = "https://www.mercadolivre.com.br/motorola"
        viewModel.uiSideEffect.observeForever(sideEffectObserver)

        // When
        viewModel.handleAction(ProductDetailUiAction.OnClickToBuy(url))

        // Then
        verifyOrder {
            sideEffectObserver.onChanged(ProductDetailUiSideEffect.OnNavigateToBrowser(url))
        }
    }

    @Test
    fun `OnClickTryAgain should call getProductDetail`() = runTest {
        // Given
        val productId = "123"
        coEvery { getProductDetailUseCase(productId) } returns flowOf(productDetailDomainMock())

        // When
        viewModel.handleAction(ProductDetailUiAction.OnClickTryAgain(productId))

        // Then
        coEvery { getProductDetailUseCase(productId) }
    }

    private fun productDetailUiModelMock(
        productId: String = "123",
        productDetail: ProductDetailUi = productDetailUiMock(),
    ): ProductDetailUiModel {
        return ProductDetailUiModel(
            productId = productId,
            productDetail = productDetail,
        )
    }

    private fun productDetailUiMock(
        id: String = "123",
        name: String = "Motorola",
        price: String = "R$ 1.499,00",
        freeShipping: Boolean = true,
    ): ProductDetailUi {
        return ProductDetailUi(
            id = id,
            title = name,
            price = price,
            pictures = listOf(
                picturesUiMock(),
                picturesUiMock(),
                picturesUiMock(),
            ),
            freeShipping = freeShipping,
            permalink = "https://www.mercadolivre.com.br/motorola",
            acceptsMercadoPago = true,
            warranty = "12 meses",
        )
    }

    private fun picturesUiMock(
        id: String = "123",
        url: String = "https://http2.mlstatic.com/D_NQ_NP_2X_901901-MLA45600093841_042021-F.webp",
    ): PictureUi {
        return PictureUi(
            id = id,
            url = url,
        )
    }

    private fun productDetailDomainMock(
        id: String = "123",
        name: String = "Motorola",
        price: Double = 1499.0,
        freeShipping: Boolean = true,
    ): ProductDetailDomain {
        return ProductDetailDomain(
            id = id,
            title = name,
            price = price,
            pictures = listOf(
                picturesDomainMock(),
                picturesDomainMock(),
                picturesDomainMock(),
            ),
            freeShipping = freeShipping,
            permalink = "https://www.mercadolivre.com.br/motorola",
            acceptsMercadoPago = true,
            warranty = "12 meses",
            condition = "new",
        )
    }

    private fun picturesDomainMock(
        id: String = "123",
        url: String = "https://http2.mlstatic.com/D_NQ_NP_2X_901901-MLA45600093841_042021-F.webp",
    ): PictureDomain {
        return PictureDomain(
            id = id,
            url = url,
        )
    }
}