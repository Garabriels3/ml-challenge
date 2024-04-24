package com.br.products.di

import com.br.infra.di.FeatureModules
import com.br.network.config.HttpClient.makeService
import com.br.products.data.repository.ProductsRepositoryImpl
import com.br.products.data.service.ProductsService
import com.br.products.domain.repository.ProductsRepository
import com.br.products.domain.usecase.products.GetProductsUseCase
import com.br.products.domain.usecase.products.GetProductsUseCaseImpl
import org.koin.core.module.Module
import org.koin.dsl.module

class ProductsDI : FeatureModules() {

    override val data: Module = module {
        single { makeService<ProductsService>() }
        factory<ProductsRepository> { ProductsRepositoryImpl(get()) }
    }

    override val domain: Module = module {
        factory<GetProductsUseCase> { GetProductsUseCaseImpl(get()) }
    }

    override val presentation: Module = module {}

    override fun getModules() = listOf(data, presentation, domain)
}