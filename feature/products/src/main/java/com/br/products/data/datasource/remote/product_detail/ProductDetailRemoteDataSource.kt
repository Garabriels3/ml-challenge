package com.br.products.data.datasource.remote.product_detail

import com.br.products.data.model.response.ProductDetailResponse
import kotlinx.coroutines.flow.Flow

interface ProductDetailRemoteDataSource {
    fun getProductDetail(productId: String): Flow<ProductDetailResponse>
}