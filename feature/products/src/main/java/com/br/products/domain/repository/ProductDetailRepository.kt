package com.br.products.domain.repository

import com.br.products.domain.model.ProductDetailDomain
import kotlinx.coroutines.flow.Flow

interface ProductDetailRepository {
    fun getProductDetail(productId: String): Flow<ProductDetailDomain>
}