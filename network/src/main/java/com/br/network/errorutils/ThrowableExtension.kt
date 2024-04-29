package com.br.network.errorutils

import com.br.network.exception.GenericException
import com.br.network.exception.NetworkException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

internal fun Throwable.toRequestThrowable(): Throwable {
    return when (this) {
        is UnknownHostException, is ConnectException -> NetworkException()
        is TimeoutException,
        is SocketTimeoutException,
        is SocketException -> GenericException()

        else -> this
    }
}