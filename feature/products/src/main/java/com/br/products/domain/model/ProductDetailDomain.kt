package com.br.products.domain.model

import com.br.infra.utils.formatPrice

data class ProductDetailDomain(
    val id: String,
    val title: String,
    val price: Double,
    val pictures: List<PictureDomain>,
    val warranty: String? = null,
    val condition: String,
    val acceptsMercadoPago: Boolean,
    val freeShipping: Boolean,
    val permalink: String,
) {
    fun priceFormatted(): String {
        return formatPrice(price)
    }
}

data class PictureDomain(
    val id: String,
    val url: String
)


