package com.br.products.domain.model

data class ProductItemDomain(
    val id: String,
    val title: String,
    val price: Double,
    val thumbnail: String,
    val condition: String,
    val availableQuantity: Int,
    val freeShipping: Boolean,
)