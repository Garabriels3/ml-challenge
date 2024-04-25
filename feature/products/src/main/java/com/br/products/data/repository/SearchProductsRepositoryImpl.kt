package com.br.products.data.repository

import com.br.products.data.datasource.local.terms.SearchProductsDataStore
import com.br.products.domain.repository.SearchProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class SearchProductsRepositoryImpl(private val searchProductsDataStore: SearchProductsDataStore) : SearchProductsRepository {
    override fun getSearchTerms(): Flow<List<String>> {
        return searchProductsDataStore.searchTerms
    }

    override suspend fun addSearchTerm(term: String) {
        val currentHistory = searchProductsDataStore.searchTerms.first().toMutableList()

        currentHistory.remove(term)
        currentHistory.add(0, term)

        if (currentHistory.size > 10) {
            currentHistory.removeLast()
        }

        searchProductsDataStore.addSearchTerm(currentHistory)
    }
}