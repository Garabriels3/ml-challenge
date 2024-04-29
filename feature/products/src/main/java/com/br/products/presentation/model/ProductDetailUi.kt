package com.br.products.presentation.model

data class ProductDetailUi(
    val id: String = "",
    val title: String = "",
    val pictures: List<PictureUi> = emptyList(),
    val price: String = "",
    val attributes: List<AttributeUi> = emptyList(),
    val warranty: String = "",
    val acceptsMercadoPago: Boolean = false,
    val freeShipping: Boolean = false,
)

data class PictureUi(
    val id: String,
    val url: String,
)

data class AttributeUi(
    val id: String,
    val name: String,
    val description: String
)