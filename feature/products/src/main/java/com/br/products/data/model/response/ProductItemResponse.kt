package com.br.products.data.model.response

import com.squareup.moshi.Json
import java.text.NumberFormat
import java.util.Locale

data class ProductItemResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "price")
    val price: Double,
    @Json(name = "thumbnail")
    val thumbnail: String,
    @Json(name = "condition")
    val condition: String,
    @Json(name = "available_quantity")
    val availableQuantity: Int,
    @Json(name = "shipping")
    val shipping: ShippingResponse,
    val total: Int = 0
) {
    fun priceFormatted(): String {
        return formatPrice(price)
    }

    fun conditionFormatted(): String {
        return when (condition) {
            Condition.NEW.name.lowercase() -> "Novo"
            Condition.USED.name.lowercase() -> "Usado"
            else -> "Não informado"
        }
    }

    private fun formatPrice(price: Double): String {
        val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        return format.format(price)
    }

    private enum class Condition {
        NEW,
        USED
    }
}

data class ShippingResponse(
    @Json(name = "free_shipping")
    val freeShipping: Boolean,
)
