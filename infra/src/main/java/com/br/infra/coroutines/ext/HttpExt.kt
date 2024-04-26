package com.br.infra.coroutines.ext

import android.net.Uri

fun String.toHttpsUri(): String {
    val uri = Uri.parse(this)
    return if (uri.scheme == "http") {
        val httpsUri = uri.buildUpon()
            .scheme("https")
            .build()
        httpsUri.toString()
    } else {
        this
    }
}