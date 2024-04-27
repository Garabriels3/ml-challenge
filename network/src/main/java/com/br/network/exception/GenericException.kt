package com.br.network.exception

private const val GENERIC_ERROR_MESSAGE = "Um erro inesperado ocorreu. Tente novamente mais tarde."

class GenericException(
    override val message: String = GENERIC_ERROR_MESSAGE
) : Throwable()