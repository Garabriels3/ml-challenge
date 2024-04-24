package com.br.products.data.model.response

import com.squareup.moshi.Json

data class ProductResponse(
    @Json(name = "paging")
    val paging: PagingResponse,
    @Json(name = "results")
    val productsItem: List<ProductItemResponse>,
)

data class PagingResponse(
    @Json(name = "total")
    val total: Int,
)
