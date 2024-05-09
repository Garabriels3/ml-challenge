package com.br.products.presentation.products.viewmodel

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
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
import com.br.products.testrules.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.verify
import io.mockk.verifyOrder
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import kotlin.test.Ignore

class ProductsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val getProductsUseCase = mockk<GetProductsUseCase>()
    private val viewModel = ProductsViewModel(getProductsUseCase)

    private val stateObserver: Observer<ProductsUiState> = mockk(relaxed = true)
    private val sideEffectObserver = mockk<Observer<ProductsUiSideEffect>>(relaxed = true)

    @Ignore("Pager data is not working with flowOf, need to fix it.")
    @Test
    fun `OnStartScreenAction with success should set correct order states`() = runTest {
        // Given
        val searchTerm = "Motorola"
        val stateLoading = slot<ProductsUiState.OnLoadingState>()
        val stateOnResumedGrid = slot<ProductsUiState>()
        coEvery { getProductsUseCase(searchTerm) } returns flowOf(
            PagingData.from(listOf(productDomainMock()))
        )

        // When
        viewModel.uiState.asLiveData().observeForever(stateObserver)
        viewModel.handleAction(action = ProductsUiAction.OnStartScreenAction(searchTerm))


        // Then
        verifyOrder {
            stateObserver.onChanged(capture(stateLoading))
            stateObserver.onChanged(capture(stateLoading))
            stateObserver.onChanged(capture(stateOnResumedGrid))
        }

        assertEquals(ProductsUiState.OnLoadingState::class, stateLoading.captured::class)
        assertEquals(ProductsUiState.OnLoadingState::class, stateLoading.captured::class)
        assertEquals(ProductsUiState.OnResumedGridState::class, stateOnResumedGrid.captured::class)
    }

    @Ignore("Pager data is not working with flowOf, need to fix it.")
    @Test
    fun `OnStartScreenAction with no connection should return NetworkException and set OnNetworkState`() =
        runTest {
            // Given
            val searchTerm = "Motorola"
            val productUiModel = productUiModelMock()
            coEvery { getProductsUseCase(searchTerm) } returns flow {
                throw NetworkException("No connection")
            }

            // When
            viewModel.uiState.asLiveData().observeForever(stateObserver)
            viewModel.handleAction(action = ProductsUiAction.OnStartScreenAction(searchTerm))

            // Then
            verifyOrder {
                stateObserver.onChanged(
                    ProductsUiState.OnLoadingState(
                        productUiModel
                    )
                )
                stateObserver.onChanged(
                    ProductsUiState.OnLoadingState(
                        productUiModel.copy(
                            searchedTerm = searchTerm
                        )
                    )
                )
                stateObserver.onChanged(
                    ProductsUiState.OnNetworkErrorState(
                        productUiModel.copy(
                            searchedTerm = searchTerm,
                            error = "No connection"
                        )
                    )
                )
            }
        }

    @Test
    fun `OnStartScreenAction with Success should return pages with correct products`() = runTest {
        // Given
        MockKAnnotations.init(this)
        mockkStatic(Log::class)
        every { Log.isLoggable(any(), any()) } returns false
        val searchTerm = "Motorola"
        val expectedProducts = listOf(
            productUiMock(
                freeShipping = R.string.products_free_shipping_label
            )
        )
        coEvery { getProductsUseCase(searchTerm) } returns flowOf(
            PagingData.from(listOf(productDomainMock()))
        )

        // When
        viewModel.uiState.asLiveData().observeForever(stateObserver)
        viewModel.handleAction(action = ProductsUiAction.OnStartScreenAction(searchTerm))

        // Then
        val actualProducts =
            (viewModel.uiState.value as? ProductsUiState.OnResumedGridState)?.uiModel?.products?.asSnapshot()
        assertEquals(expectedProducts, actualProducts)
    }

    @Test
    fun `OnStartScreenAction with Network Error should set Network State Error`() = runTest {
        // Given
        MockKAnnotations.init(this)
        mockkStatic(Log::class)
        every { Log.isLoggable(any(), any()) } returns false
        val searchTerm = "Motorola"
        val expectedErrorMessage = "No connection"
        coEvery { getProductsUseCase(searchTerm) } returns flow {
            throw NetworkException("No connection")
        }

        // When
        viewModel.uiState.asLiveData().observeForever(stateObserver)
        viewModel.handleAction(action = ProductsUiAction.OnStartScreenAction(searchTerm))

        // Then
        val actualError =
            (viewModel.uiState.value as? ProductsUiState.OnNetworkErrorState)?.uiModel?.error
        assertEquals(expectedErrorMessage, actualError)
    }

    @Test
    fun `OnStartScreenAction with Generic Error should set Generic State Error`() = runTest {
        // Given
        MockKAnnotations.init(this)
        mockkStatic(Log::class)
        every { Log.isLoggable(any(), any()) } returns false
        val searchTerm = "Motorola"
        val expectedErrorMessage = "Unknown error"
        coEvery { getProductsUseCase(searchTerm) } returns flow {
            throw Exception()
        }

        // When
        viewModel.uiState.asLiveData().observeForever(stateObserver)
        viewModel.handleAction(action = ProductsUiAction.OnStartScreenAction(searchTerm))

        // Then
        val actualError =
            (viewModel.uiState.value as? ProductsUiState.OnErrorState)?.uiModel?.error
        assertEquals(expectedErrorMessage, actualError)
    }

    @Test
    fun `OnClickSearchBarAction should emit OnBackToSearchEffect`() {
        // Given
        viewModel.uiSideEffect.observeForever(sideEffectObserver)

        // When
        viewModel.handleAction(ProductsUiAction.OnClickSearchBarAction)

        // Then
        verify {
            sideEffectObserver.onChanged(ProductsUiSideEffect.OnBackToSearchEffect)
        }
    }

    @Test
    fun `OnProductClickAction should emit OnNavigateToDetailEffect`() {
        // Given
        val productId = "123"
        viewModel.uiSideEffect.observeForever(sideEffectObserver)

        // When
        viewModel.handleAction(ProductsUiAction.OnProductClickAction(productId))

        // Then
        verify {
            sideEffectObserver.onChanged(ProductsUiSideEffect.OnNavigateToDetailEffect(productId))
        }
    }

    @Test
    fun `OnProductsOrientationClickAction when is LIST orientation should change orientation to GRID`() = runTest {
        // Given
        val orientation = ProductsOrientation.LIST
        val expectedOrientation = ProductsOrientation.GRID

        // When
        viewModel.handleAction(ProductsUiAction.OnProductsOrientationClickAction(orientation))

        // Then
        val actualOrientation =
            (viewModel.uiState.value as? ProductsUiState.OnResumedListState)?.uiModel?.productsOrientation
        assertEquals(expectedOrientation, actualOrientation)
    }

    @Test
    fun `OnProductsOrientationClickAction when is GRID orientation should change orientation to LIST`() = runTest {
        // Given
        val orientation = ProductsOrientation.GRID
        val expectedOrientation = ProductsOrientation.LIST

        // When
        viewModel.handleAction(ProductsUiAction.OnProductsOrientationClickAction(orientation))

        // Then
        val actualOrientation =
            (viewModel.uiState.value as? ProductsUiState.OnResumedGridState)?.uiModel?.productsOrientation
        assertEquals(expectedOrientation, actualOrientation)
    }

    private fun productUiModelMock(
        searchedTerm: String = "",
        total: Int = 0,
        message: String? = null,
        products: PagingData<ProductUi> = PagingData.empty()
    ): ProductsUiModel {
        return ProductsUiModel(
            error = message,
            searchedTerm = searchedTerm,
            totalProducts = total,
            products = flowOf(
                products
            )
        )
    }

    private fun productUiMock(
        id: String = "123",
        name: String = "Motorola",
        price: String = "R$ 1.499,00",
        imageUrl: String = "https://http2.mlstatic.com/D_NQ_NP_2X_901901-MLA45600093841_042021-F.webp",
        condition: String = "Novo",
        availableQuantity: Int = 0,
        freeShipping: Int = 0,
    ): ProductUi {
        return ProductUi(
            id = id,
            name = name,
            price = price,
            imageUrl = imageUrl,
            condition = condition,
            availableQuantity = availableQuantity,
            freeShipping = freeShipping,
        )
    }

    private fun productDomainMock(): ProductItemDomain {
        return ProductItemDomain(
            title = "Motorola",
            price = 1499.0,
            thumbnail = "https://http2.mlstatic.com/D_NQ_NP_2X_901901-MLA45600093841_042021-F.webp",
            condition = "Novo",
            availableQuantity = 0,
            freeShipping = true,
            id = "123"
        )
    }
}