package com.br.products.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import app.cash.turbine.test
import com.br.network.config.HttpClient.makeService
import com.br.products.data.service.ProductsService
import com.br.products.domain.model.ProductItemDomain
import com.br.products.domain.repository.ProductsRepository
import dev.thiagosouto.butler.file.readFile
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class ProductsRepositoryImplTest {

    private val mockWebServer = MockWebServer()
    private val productsRepository = createRepository()

    @Before
    fun setup() {
        mockWebServer.start()
        mockWebServer.url("sites/MLB/search").toString()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getProductsPagingData Should return a mapped PaggingData When sucess`(): Unit =
        runBlocking {
            // Given
            mockWebServer.enqueue(MockResponse().setBody(readFile("json/products/products.json")))
            val actualMutableList = mutableListOf<ProductItemDomain>()
            val expectedMutableList = mutableListOf<ProductItemDomain>()
            createRepository()

            // When
            val response = productsRepository.getProductsPagingData("Motorola", 5)

            // Then
            response.test {
                pagingDataRepoItemMock().map {
                    actualMutableList.add(it)
                }
                val actualData = awaitItem()
                actualData.map {
                    expectedMutableList.add(it)
                }

                assertEquals(expectedMutableList, actualMutableList)
            }
        }

    private fun createRepository(): ProductsRepository {
        val service = makeService<ProductsService>()
        return ProductsRepositoryImpl(service, Dispatchers.Default)
    }

    private fun pagingDataRepoItemMock(): PagingData<ProductItemDomain> {
        return PagingData.from(
            listOf(
                ProductItemDomain(
                    id = "MLA810645372",
                    title = "Motorola G6 Plus 64 Gb Nimbus",
                    price = "R$ 1000,00",
                    thumbnail = "http://mla-s2-p.mlstatic.com/795558-MLA31003306206_062019-I.jpg",
                    condition = "new",
                    availableQuantity = 100,
                    freeShipping = false,
                    total = 916
                )
            )
        )
    }
}