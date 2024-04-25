package com.br.products.domain.usecase.terms

import com.br.products.domain.repository.SearchProductsRepository

class SaveTermsHistoryUseCaseImpl(private val searchProductsRepository: SearchProductsRepository) :
    SaveTermsHistoryUseCase {
    override suspend fun invoke(term: String) {
        searchProductsRepository.addSearchTerm(term)
    }
}