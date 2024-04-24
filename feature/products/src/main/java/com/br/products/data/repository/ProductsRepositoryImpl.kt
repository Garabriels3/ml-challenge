package com.br.products.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.br.network.errorutils.parseHttpError
import com.br.products.data.datasource.remote.ProductPagingSource
import com.br.products.data.model.response.ProductItemResponse
import com.br.products.data.model.response.ShippingResponse
import com.br.products.data.service.ProductsService
import com.br.products.domain.model.ProductItemDomain
import com.br.products.domain.model.ShippingDomain
import com.br.products.domain.repository.ProductsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class ProductsRepositoryImpl(
    private val service: ProductsService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProductsRepository {

    override fun getProductsPagingData(
        query: String,
        limit: Int
    ): Flow<PagingData<ProductItemDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = limit,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ProductPagingSource(service, query) }
        ).flow.map { page ->
            page.map {
                it.toDomain()
            }
        }
            .flowOn(dispatcher)
            .parseHttpError()
    }

    private fun ProductItemResponse.toDomain(): ProductItemDomain {
        return ProductItemDomain(
            id = id,
            title = title,
            price = price,
            thumbnail = thumbnail,
            condition = condition,
            availableQuantity = availableQuantity,
            shipping = shipping.toDomain()
        )
    }

    private fun ShippingResponse.toDomain(): ShippingDomain {
        return ShippingDomain(
            freeShipping = freeShipping
        )
    }
}