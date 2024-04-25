package com.br.products.data.datasource.local.terms

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SearchProductsDataStoreImpl(private val dataStore: DataStore<Preferences>) :
    SearchProductsDataStore {

    override val searchTerms: Flow<List<String>> = dataStore.data
        .map { preferences ->
            preferences[SEARCH_HISTORY_KEY]?.split(",") ?: emptyList()
        }
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyList())
            } else {
                throw exception
            }
        }

    override suspend fun addSearchTerm(terms: List<String>) {
        dataStore.edit { preferences ->
            preferences[SEARCH_HISTORY_KEY] = terms.joinToString(",")
        }
    }

    companion object {
        private val SEARCH_HISTORY_KEY = stringPreferencesKey("search_history")
    }
}