package com.example.josephbellibankofamericacc.data.repository

import com.example.josephbellibankofamericacc.data.entity.CountryEntity
import com.example.josephbellibankofamericacc.data.network.CountryNetworkModel
import com.example.josephbellibankofamericacc.data.service.CountriesService
import com.example.josephbellibankofamericacc.database.CountriesDao
import com.example.josephbellibankofamericacc.domain.CountriesRepository
import retrofit2.Response
import javax.inject.Inject

class CountriesRepositoryImpl @Inject constructor(
    private val service: CountriesService,
    private val countriesDao: CountriesDao
) : CountriesRepository {

    override suspend fun getNetworkCountries(): Response<List<CountryNetworkModel>> =
        service.callCountriesApi()

    override suspend fun insertAllLocalCountries(countries: List<CountryEntity>) =
        countriesDao.insertAllCountries(countries = countries)

    override suspend fun getLocalCountryDetail(countryCode: String): CountryEntity? =
        countriesDao.getCountry(countryCode = countryCode)

    override suspend fun getLocalCountryList(): List<CountryEntity> =
        countriesDao.getAllCountries()

}