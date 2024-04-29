package com.br.products.presentation.model

data class ProductDetailUi(
    val id: String = "",
    val title: String = "",
    val pictures: List<PictureUi> = emptyList(),
    val price: String = "",
    val warranty: String? = null,
    val acceptsMercadoPago: Boolean = false,
    val freeShipping: Boolean = false,
    val permalink: String = "",
)

data class PictureUi(
    val id: String,
    val url: String,
)