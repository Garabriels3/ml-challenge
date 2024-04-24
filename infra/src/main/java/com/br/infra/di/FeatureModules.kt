package com.br.infra.di

import org.koin.core.module.Module
abstract class FeatureModules {
    abstract val data: Module
    abstract val presentation: Module
    abstract val domain: Module

    open fun getModules() = listOf(data, presentation, domain)
}