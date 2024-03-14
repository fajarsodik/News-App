package com.hevadevelop.domain.network.di

import com.hevadevelop.domain.BuildConfig.base_url
import com.hevadevelop.domain.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    fun provideBaseUrl() : String = base_url

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(90, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideRestAdapter(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        val retro = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
        return retro
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =retrofit.create(ApiService::class.java)

}