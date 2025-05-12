package com.example.josephbellibankofamericacc.di

import com.example.josephbellibankofamericacc.data.service.CountriesService
import com.example.josephbellibankofamericacc.data.util.BASE_URL
import com.example.josephbellibankofamericacc.data.util.NETWORK_TIMEOUT
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun providesGson(): Gson = Gson()

    @Provides
    @Singleton
    fun providesOkHttp(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun providesRetrofit(
        client: OkHttpClient,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun providesService(
        retrofit: Retrofit
    ): CountriesService = retrofit.create(CountriesService::class.java)

    @Provides
    @Singleton
    fun providesDispatcher(): CoroutineDispatcher = Dispatchers.IO

}