package com.br.products.data.model.response

import com.squareup.moshi.Json

data class ProductDetailResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "price")
    val price: Double,
    @Json(name = "pictures")
    val pictures: List<PictureResponse>,
    @Json(name = "condition")
    val condition: String,
    @Json(name = "permalink")
    val permalink: String,
    @Json(name = "shipping")
    val shipping: ShippingResponse,
    @Json(name = "warranty")
    val warranty: String? = null,
    @Json(name = "accepts_mercadopago")
    val acceptsMercadoPago: Boolean,
)

data class PictureResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "secure_url")
    val url: String
)
