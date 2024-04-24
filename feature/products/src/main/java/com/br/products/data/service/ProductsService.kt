package com.br.products.data.service

import com.br.products.data.model.response.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsService {

    @GET("sites/MLB/search")
    suspend fun getProducts(
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): ProductResponse
}
