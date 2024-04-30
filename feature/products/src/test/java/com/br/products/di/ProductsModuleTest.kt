package com.br.products.di

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.robolectric.RuntimeEnvironment
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
class ProductsModuleTest : KoinTest {

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `check all modules`() {
        startKoin {
            androidContext(RuntimeEnvironment.getApplication())
            modules(ProductsDI().getModules())
        }.checkModules()
    }
}