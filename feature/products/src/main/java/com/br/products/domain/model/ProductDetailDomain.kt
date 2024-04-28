package com.br.products.domain.model

import com.br.infra.utils.formatPrice

data class ProductDetailDomain(
    val id: String,
    val title: String,
    val price: Double,
    val pictures: List<PictureDomain>,
    val attributes: List<AttributeDomain>,
    val warranty: String,
    val condition: String,
    val acceptsMercadoPago: Boolean,
    val freeShipping: Boolean,
) {
    fun priceFormatted(): String {
        return formatPrice(price)
    }
}

data class AttributeDomain(
    val id: String,
    val name: String,
    val description: String
)

data class PictureDomain(
    val id: String,
    val url: String
)


