package com.br.products.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.br.network.config.HttpClient.makeService
import com.br.products.data.datasource.local.terms.SearchProductsDataStore
import com.br.products.data.datasource.local.terms.SearchProductsDataStoreImpl
import com.br.products.data.repository.ProductsRepositoryImpl
import com.br.products.data.repository.SearchProductsRepositoryImpl
import com.br.products.data.service.ProductsService
import com.br.products.domain.repository.ProductsRepository
import com.br.products.domain.repository.SearchProductsRepository
import com.br.products.domain.usecase.products.GetProductsUseCase
import com.br.products.domain.usecase.products.GetProductsUseCaseImpl
import com.br.products.domain.usecase.terms.GetTermsHistoryUseCase
import com.br.products.domain.usecase.terms.GetTermsHistoryUseCaseImpl
import com.br.products.domain.usecase.terms.SaveTermsHistoryUseCase
import com.br.products.domain.usecase.terms.SaveTermsHistoryUseCaseImpl
import com.br.products.presentation.products.viewmodel.ProductsViewModel
import com.br.products.presentation.searchproduct.viewmodel.SearchProductViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class ProductsDI {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "search_history")

    private val data: Module = module {
        single { makeService<ProductsService>() }
        single { provideDataStore(androidContext()) }
        factory<SearchProductsDataStore> { SearchProductsDataStoreImpl(get()) }
        factory<SearchProductsRepository> { SearchProductsRepositoryImpl(get()) }
        factory<ProductsRepository> { ProductsRepositoryImpl(get()) }
    }

    private val domain: Module = module {
        factory<GetProductsUseCase> { GetProductsUseCaseImpl(get()) }
        factory<GetTermsHistoryUseCase> { GetTermsHistoryUseCaseImpl(get()) }
        factory<SaveTermsHistoryUseCase> { SaveTermsHistoryUseCaseImpl(get()) }
    }

    private val presentation: Module = module {
        viewModel { SearchProductViewModel(get(), get()) }
        viewModel { ProductsViewModel(get()) }
    }

    fun getModules() = listOf(data, presentation, domain)

    private fun provideDataStore(context: Context): DataStore<Preferences> =
        context.dataStore
}