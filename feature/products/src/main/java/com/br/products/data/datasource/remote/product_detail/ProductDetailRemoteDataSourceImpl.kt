package com.br.products.data.datasource.remote.product_detail

import com.br.products.data.model.response.ProductDetailResponse
import com.br.products.data.service.ProductDetailService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductDetailRemoteDataSourceImpl(
     private val productDetailService: ProductDetailService,
        private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProductDetailRemoteDataSource {
    override fun getProductDetail(productId: String): Flow<ProductDetailResponse> {
        return flow {
            emit(productDetailService.getProductDetail(productId))
        }.flowOn(dispatcher)
    }
}