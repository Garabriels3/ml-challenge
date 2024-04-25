package com.br.products.di

import com.br.network.config.HttpClient.makeService
import com.br.products.data.repository.ProductsRepositoryImpl
import com.br.products.data.service.ProductsService
import com.br.products.domain.repository.ProductsRepository
import com.br.products.domain.usecase.products.GetProductsUseCase
import com.br.products.domain.usecase.products.GetProductsUseCaseImpl
import com.br.products.presentation.searchproduct.viewmodel.SearchProductViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class ProductsDI {

    private val data: Module = module {
        single { makeService<ProductsService>() }
        factory<ProductsRepository> { ProductsRepositoryImpl(get()) }
    }

    private val domain: Module = module {
        factory<GetProductsUseCase> { GetProductsUseCaseImpl(get()) }
    }

    private val presentation: Module = module {
        viewModel { SearchProductViewModel() }
    }

    fun getModules() = listOf(data, presentation, domain)
}