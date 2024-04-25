package com.br.products.domain.usecase.terms

interface SaveTermsHistoryUseCase {
    suspend operator fun invoke(term: String)
}