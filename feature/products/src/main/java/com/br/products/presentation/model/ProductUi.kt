package com.br.products.presentation.model

data class ProductUi(
    val id: String,
    val name: String,
    val price: String,
    val imageUrl: String,
    val condition: String,
    val availableQuantity: Int,
    val freeShipping: Int?,
    val total: Int,
)
