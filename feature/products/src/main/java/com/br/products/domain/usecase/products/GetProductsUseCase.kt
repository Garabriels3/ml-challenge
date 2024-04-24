package com.br.products.domain.usecase.products

import androidx.paging.PagingData
import com.br.products.domain.model.ProductItemDomain
import kotlinx.coroutines.flow.Flow

interface GetProductsUseCase {
    fun getProducts(query: String): Flow<PagingData<ProductItemDomain>>
}