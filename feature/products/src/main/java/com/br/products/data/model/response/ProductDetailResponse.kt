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
    val warranty: String,
    @Json(name = "attributes")
    val attributes: List<AttributeResponse>,
    @Json(name = "accepts_mercadopago")
    val acceptsMercadoPago: Boolean,
)

data class AttributeResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "value_name")
    val description: String
)

data class PictureResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "secure_url")
    val url: String
)
