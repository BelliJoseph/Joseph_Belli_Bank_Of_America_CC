package com.example.josephbellibankofamericacc.data.service

import com.example.josephbellibankofamericacc.data.network.CountryNetworkModel
import com.example.josephbellibankofamericacc.data.util.PATH
import retrofit2.Response
import retrofit2.http.GET

interface CountriesService {

    @GET(PATH)
    suspend fun callCountriesApi(): Response<List<CountryNetworkModel>>
}