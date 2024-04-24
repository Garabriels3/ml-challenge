package com.br.products.domain.model

data class ProductItemDomain(
    val id: String,
    val title: String,
    val price: Double,
    val thumbnail: String,
    val condition: String,
    val availableQuantity: Int,
    val shipping: ShippingDomain,
)

data class ShippingDomain(
    val freeShipping: Boolean,
)
