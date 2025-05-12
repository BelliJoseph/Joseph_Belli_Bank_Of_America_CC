package com.example.josephbellibankofamericacc.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.josephbellibankofamericacc.data.domain.CountryDetailDomainModel
import com.example.josephbellibankofamericacc.data.util.UiState
import com.example.josephbellibankofamericacc.domain.GetCountryDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryDetailViewModel @Inject constructor(
    private val getCountryDetailUseCase: GetCountryDetailUseCase,
) : ViewModel() {

    private val _countryDetails: MutableStateFlow<UiState<CountryDetailDomainModel>> =
        MutableStateFlow(
            UiState.LOADING
        )
    val countryDetails: StateFlow<UiState<CountryDetailDomainModel>> get() =
        _countryDetails.asStateFlow()

    fun getCountryDetails(countryCode: String) {
        viewModelScope.launch {
            getCountryDetailUseCase.invoke(code = countryCode).collect { state ->
                _countryDetails.update {
                    state
                }
            }
        }
    }
}