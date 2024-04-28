package com.br.products.presentation.products.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.br.network.exception.NetworkException
import com.br.products.domain.model.ProductItemDomain
import com.br.products.domain.usecase.products.GetProductsUseCase
import com.br.products.presentation.products.udf.ProductUi
import com.br.products.presentation.products.udf.ProductsUiAction
import com.br.products.presentation.products.udf.ProductsUiModel
import com.br.products.presentation.products.udf.ProductsUiSideEffect
import com.br.products.presentation.products.udf.ProductsUiState
import com.br.products.testrules.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ProductsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val getProductsUseCase = mockk<GetProductsUseCase>(relaxed = true)
    private val viewModel = ProductsViewModel(getProductsUseCase)

    private val stateObserver = mockk<Observer<ProductsUiState>>(relaxed = true)
    private val sideEffectObserver = mockk<Observer<ProductsUiSideEffect>>(relaxed = true)

    @Before
    fun setup() {
        viewModel.uiState.asLiveData().observeForever(stateObserver)
        viewModel.uiSideEffect.observeForever(sideEffectObserver)
    }

    @After
    fun teardown() {
        viewModel.uiState.asLiveData().removeObserver(stateObserver)
        viewModel.uiSideEffect.removeObserver(sideEffectObserver)
    }

    @Test
    fun `OnStartScreenAction with Success should return pages of products`() = runBlocking {
        // Given
        val searchTerm = "Motorola"
        val productUiModelLoading = productUiModelMock(
            searchedTerm = searchTerm,
            total = 0,
        )
        val productUiModelOnResumedGrid = productUiModelMock(
            searchedTerm = searchTerm,
            total = 0,
            products = PagingData.from(listOf(productUiMock()))
        )
        coEvery { getProductsUseCase(searchTerm) } returns flowOf(
            PagingData.from(listOf(productDomainMock()))
        )


        // When
        viewModel.handleAction(action = ProductsUiAction.OnStartScreenAction(searchTerm))

        // Then
        verifyOrder {
            stateObserver.onChanged(
                ProductsUiState.OnLoadingState(
                    productUiModelLoading.copy(
                        searchedTerm = ""
                    )
                )
            )
            stateObserver.onChanged(ProductsUiState.OnLoadingState(productUiModelLoading))
            stateObserver.onChanged(ProductsUiState.OnResumedGridState(productUiModelOnResumedGrid))
        }
    }

    @Test
    fun `OnStartScreenAction with no connection should return NetworkException and set OnNetworkState`() =
        runBlocking {
            // Given
            val searchTerm = "Motorola"
            val productUiModelLoading = productUiModelMock(
                searchedTerm = searchTerm,
                total = 0,
            )
            coEvery { getProductsUseCase(searchTerm) } throws NetworkException("No connection")

            // When
            viewModel.handleAction(action = ProductsUiAction.OnStartScreenAction(searchTerm))

            // Then
            verifyOrder {
                stateObserver.onChanged(
                    ProductsUiState.OnLoadingState(
                        productUiModelLoading.copy(
                            searchedTerm = ""
                        )
                    )
                )
                stateObserver.onChanged(ProductsUiState.OnLoadingState(productUiModelLoading))
                stateObserver.onChanged(
                    ProductsUiState.OnNetworkState(
                        productUiModelMock()
                    )
                )
            }
        }


    private fun productUiModelMock(
        searchedTerm: String = "",
        total: Int = 0,
        products: PagingData<ProductUi> = PagingData.empty()
    ): ProductsUiModel {
        return ProductsUiModel(
            searchedTerm = searchedTerm,
            totalProducts = total,
            products = flowOf(
                products
            )
        )
    }

    private fun productUiMock(): ProductUi {
        return ProductUi(
            id = "123",
            name = "Motorola",
            price = "R$ 1.499,00",
            imageUrl = "https://http2.mlstatic.com/D_NQ_NP_2X_901901-MLA45600093841_042021-F.webp",
            condition = "Novo",
            availableQuantity = 0,
            freeShipping = 0,
            total = 0
        )
    }

    private fun productDomainMock(): ProductItemDomain {
        return ProductItemDomain(
            title = "Motorola",
            total = 0,
            price = "R$ 1.499,00",
            thumbnail = "https://http2.mlstatic.com/D_NQ_NP_2X_901901-MLA45600093841_042021-F.webp",
            condition = "Novo",
            availableQuantity = 0,
            freeShipping = true,
            id = "123"
        )
    }
}