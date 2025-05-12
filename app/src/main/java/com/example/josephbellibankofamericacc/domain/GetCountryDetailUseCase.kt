package com.example.josephbellibankofamericacc.domain

import com.example.josephbellibankofamericacc.data.domain.CountryDetailDomainModel
import com.example.josephbellibankofamericacc.domain.CountriesRepository
import com.example.josephbellibankofamericacc.data.util.NullResponse
import com.example.josephbellibankofamericacc.data.util.UiState
import com.example.josephbellibankofamericacc.data.util.toDomainDetail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCountryDetailUseCase @Inject constructor(
    private val countriesRepository: CountriesRepository,
    private val dispatcher: CoroutineDispatcher
) {

    operator fun invoke(
        code: String
    ): Flow<UiState<CountryDetailDomainModel>> = flow {
        emit(UiState.LOADING)
        try {

            val countryEntity = countriesRepository.getLocalCountryDetail(
                countryCode = code
            )

            countryEntity?.let {
                emit(UiState.SUCCESS(it.toDomainDetail()))

            } ?: throw NullResponse("No Country Found with that Code")

        } catch (e: Exception) {
            emit(UiState.ERROR(e))
        }

    }.flowOn(dispatcher)
}