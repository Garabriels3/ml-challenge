package com.br.products.domain.usecase.terms

import com.br.products.domain.repository.SearchProductsRepository
import kotlinx.coroutines.flow.Flow

class GetTermsHistoryUseCaseImpl(
    private val searchProductsRepository: SearchProductsRepository
) : GetTermsHistoryUseCase {
    override fun invoke(): Flow<List<String>> {
        return searchProductsRepository.getSearchTerms()
    }
}