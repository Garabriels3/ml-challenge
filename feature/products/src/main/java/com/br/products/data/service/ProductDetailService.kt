package com.br.products.data.service

import com.br.products.data.model.response.ProductDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductDetailService {

    @GET("items/{productId}")
    suspend fun getProductDetail(@Path("productId") productId: String): ProductDetailResponse
}