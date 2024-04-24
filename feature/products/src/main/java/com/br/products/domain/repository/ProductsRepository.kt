package com.br.products.domain.repository

import androidx.paging.PagingData
import com.br.products.domain.model.ProductItemDomain
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    fun getProductsPagingData(query: String, limit: Int): Flow<PagingData<ProductItemDomain>>
}