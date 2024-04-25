package com.br.products.domain.usecase.terms

import kotlinx.coroutines.flow.Flow

interface GetTermsHistoryUseCase {
    operator fun invoke(): Flow<List<String>>
}