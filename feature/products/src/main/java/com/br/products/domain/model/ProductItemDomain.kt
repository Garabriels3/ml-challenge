package com.br.products.domain.model

import com.br.infra.utils.formatPrice

data class ProductItemDomain(
    val id: String,
    val title: String,
    val price: Double,
    val thumbnail: String,
    val condition: String,
    val availableQuantity: Int,
    val freeShipping: Boolean,
) {
    fun priceFormatted(): String {
        return formatPrice(price)
    }
}