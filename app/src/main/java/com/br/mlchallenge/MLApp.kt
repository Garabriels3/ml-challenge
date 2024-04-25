package com.br.mlchallenge

import android.app.Application
import com.br.products.di.ProductsDI
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class MLApp : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MLApp)
            modules(ProductsDI().getModules())
        }
    }
}