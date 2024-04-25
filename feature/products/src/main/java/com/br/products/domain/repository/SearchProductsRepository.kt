package com.br.products.domain.repository

import kotlinx.coroutines.flow.Flow

interface SearchProductsRepository {
    fun getSearchTerms() : Flow<List<String>>

    suspend fun addSearchTerm(term: String)
}