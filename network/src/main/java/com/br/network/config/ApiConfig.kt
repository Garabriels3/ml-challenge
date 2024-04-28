package com.br.network.config

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object HttpClient {

    inline fun <reified T> makeService(baseUrl: String? = null): T {
        return retrofitBuild(baseUrl).create(T::class.java)
    }

    fun retrofitBuild(baseUrl: String?): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl ?: "https://api.mercadolibre.com/")
            .client(okHttpBuild())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshiFactory()).asLenient())
            .build()
    }

    private fun moshiFactory(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private fun okHttpBuild(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = getLogLevel() })
            .build()
    }

    private fun getLogLevel() = HttpLoggingInterceptor.Level.BODY
}