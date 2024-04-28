package com.br.products.data.repository

import android.util.Log
import androidx.paging.testing.asSnapshot
import com.br.network.config.HttpClient.makeService
import com.br.products.data.service.ProductsService
import com.br.products.domain.repository.ProductsRepository
import com.br.products.stubs.productsDomainMock
import dev.thiagosouto.butler.file.readFile
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockkStatic
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class ProductsRepositoryImplTest {

    private val mockWebServer = MockWebServer()
    private lateinit var productsRepository: ProductsRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mockkStatic(Log::class)
        every { Log.isLoggable(any(), any()) } returns false
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getProductsPagingData Should return a mapped PaggingData When sucess`(): Unit =
        runTest {
            // Given
            mockWebServer.enqueue(MockResponse().setBody(readFile("json/products/products.json")))
            productsRepository = createRepository(mockWebServer.url("/").toString())
            val expectedMutableList = productsDomainMock()

            // When
            val response = productsRepository.getProductsPagingData("Motorola", 2).asSnapshot()

            // Then
            assertEquals(expectedMutableList, response)
        }

    @Test
    fun `getProductsPagingData with limit and scroll to index should to return correct size list`(): Unit =
        runTest {
            // Given
            mockWebServer.enqueue(MockResponse().setBody(readFile("json/products/products.json")))
            productsRepository = createRepository()
            val expectedMutableList = productsDomainMock()

            // When
            val response = productsRepository.getProductsPagingData("Motorola", 2).asSnapshot {
                scrollTo(6)
            }

            // Then
            assertEquals(10, response.size)
        }

    private fun createRepository(baseUrl: String? = null): ProductsRepository {
        val service = makeService<ProductsService>(baseUrl)
        return ProductsRepositoryImpl(service, Dispatchers.Unconfined)
    }
}