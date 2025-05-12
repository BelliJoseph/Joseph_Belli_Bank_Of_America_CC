package com.example.josephbellibankofamericacc.domain

import com.example.josephbellibankofamericacc.data.entity.CountryEntity
import com.example.josephbellibankofamericacc.data.network.CountryNetworkModel
import retrofit2.Response

interface CountriesRepository {

    suspend fun getNetworkCountries(): Response<List<CountryNetworkModel>>

    suspend fun insertAllLocalCountries(countries: List<CountryEntity>)

    suspend fun getLocalCountryDetail(
        countryCode: String
    ): CountryEntity?

    suspend fun getLocalCountryList(): List<CountryEntity>
}