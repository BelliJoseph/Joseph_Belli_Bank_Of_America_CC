package com.example.josephbellibankofamericacc.viewmodel

import com.example.josephbellibankofamericacc.data.util.ResponseFailure
import com.example.josephbellibankofamericacc.data.util.UiState
import com.example.josephbellibankofamericacc.domain.GetCountriesListUseCase
import com.example.josephbellibankofamericacc.presentation.viewmodel.CountryListViewModel
import com.example.josephbellibankofamericacc.testData.countryListDomainTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CountryListViewModelTest {

    private lateinit var countryListViewModel: CountryListViewModel
    private lateinit var getCountriesListUseCase: GetCountriesListUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        getCountriesListUseCase = mockk<GetCountriesListUseCase>()
    }

    private fun setUpViewModel() {
        countryListViewModel = CountryListViewModel(getCountriesListUseCase)
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun `getCountriesList updates countryList state flow with data from useCase`() = runTest {
        //Arrange
        val flow = flowOf(
            UiState.SUCCESS(countryListDomainTest)
        )

        coEvery { getCountriesListUseCase() } returns flow

        //Act
        setUpViewModel()
        countryListViewModel.countryList.drop(1).first()

        //Assert
        assertEquals(countryListViewModel.countryList.value, UiState.SUCCESS(countryListDomainTest))
        coVerify(exactly = 1) { getCountriesListUseCase.invoke() }
    }

    @Test
    fun `getCountriesList updates countryList state flow with error from useCase`() = runTest {
        //Arrange
        val exception = ResponseFailure("Network Error")

        val flow = flowOf(
            UiState.ERROR(exception)
        )

        coEvery { getCountriesListUseCase.invoke() } returns flow

        //Act
        setUpViewModel()
        countryListViewModel.countryList.drop(1).first()

        //Assert
        assertEquals(countryListViewModel.countryList.value, UiState.ERROR(exception))
        coVerify(exactly = 1) { getCountriesListUseCase.invoke() }
    }

}