package com.example.josephbellibankofamericacc.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.josephbellibankofamericacc.data.domain.CountryListDomainModel
import com.example.josephbellibankofamericacc.data.util.UiState
import com.example.josephbellibankofamericacc.domain.GetCountriesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val getCountriesListUseCase: GetCountriesListUseCase,
) : ViewModel() {

    private val _countryList: MutableStateFlow<UiState<List<CountryListDomainModel>>> =
        MutableStateFlow(
            UiState.LOADING
        )
    val countryList: StateFlow<UiState<List<CountryListDomainModel>>> get() = _countryList.asStateFlow()

    init {
        //Load data on creation
        getCountriesList()
    }

    fun getCountriesList() {
        viewModelScope.launch {
            getCountriesListUseCase().collect { state ->
                _countryList.value = state
            }
        }
    }
}