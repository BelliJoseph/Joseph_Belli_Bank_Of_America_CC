package com.example.josephbellibankofamericacc.di

import com.example.josephbellibankofamericacc.domain.CountriesRepository
import com.example.josephbellibankofamericacc.data.repository.CountriesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsCountriesRepository(
        countriesRepositoryImpl: CountriesRepositoryImpl
    ): CountriesRepository
}