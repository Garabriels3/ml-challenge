package com.br.products.data.datasource.local.terms

import kotlinx.coroutines.flow.Flow

interface SearchProductsDataStore {

    val searchTerms: Flow<List<String>>
    suspend fun addSearchTerm(terms: List<String>)
}