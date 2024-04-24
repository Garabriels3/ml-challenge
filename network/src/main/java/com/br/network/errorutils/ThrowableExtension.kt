package com.br.network.errorutils

import com.br.network.exception.NetworkException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

internal fun getDefaultThrowable() = NetworkException()

internal fun Throwable.toRequestThrowable(): Throwable {
    return when (this) {
        is UnknownHostException,
        is TimeoutException,
        is InterruptedIOException,
        is SocketTimeoutException,
        is SocketException,
        is ConnectException -> getDefaultThrowable()
        else -> this
    }
}