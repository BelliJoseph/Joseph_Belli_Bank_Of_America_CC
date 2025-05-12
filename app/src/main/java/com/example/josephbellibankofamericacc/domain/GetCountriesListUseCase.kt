package com.example.josephbellibankofamericacc.domain

import com.example.josephbellibankofamericacc.data.domain.CountryListDomainModel
import com.example.josephbellibankofamericacc.data.util.NullResponse
import com.example.josephbellibankofamericacc.data.util.ResponseFailure
import com.example.josephbellibankofamericacc.data.util.UiState
import com.example.josephbellibankofamericacc.data.util.toDomainList
import com.example.josephbellibankofamericacc.data.util.toEntityList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCountriesListUseCase @Inject constructor(
    private val countriesRepository: CountriesRepository,
    private val dispatcher: CoroutineDispatcher
) {

    operator fun invoke(): Flow<UiState<List<CountryListDomainModel>>> = flow {
        emit(UiState.LOADING)
        try {

            val countryList = countriesRepository.getLocalCountryList()

            if (countryList.isNotEmpty()) {
                emit(UiState.SUCCESS(countryList.toDomainList()))
            } else {
                val networkResult = countriesRepository.getNetworkCountries()

                if (networkResult.isSuccessful) {
                    networkResult.body()?.let {
                        countriesRepository.insertAllLocalCountries(
                            countries = it.toEntityList()
                        )

                        val entities = countriesRepository.getLocalCountryList()

                        if (entities.isNotEmpty()) {
                            emit(UiState.SUCCESS(entities.toDomainList()))
                        } else {
                            throw NullResponse()
                        }
                    } ?: throw NullResponse()

                } else {
                    throw ResponseFailure(networkResult.errorBody()?.string())
                }

            }

        } catch (e: Exception) {
            emit(UiState.ERROR(e))
        }

    }.flowOn(dispatcher)
}