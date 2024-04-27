package com.br.infra.utils

import java.text.NumberFormat
import java.util.Locale

fun formatPrice(price: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return format.format(price)
}