package com.example.josephbellibankofamericacc.viewmodel

import com.example.josephbellibankofamericacc.data.util.NullResponse
import com.example.josephbellibankofamericacc.data.util.UiState
import com.example.josephbellibankofamericacc.domain.GetCountryDetailUseCase
import com.example.josephbellibankofamericacc.presentation.viewmodel.CountryDetailViewModel
import com.example.josephbellibankofamericacc.testData.countryDetailDomainModelTest
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
class CountryDetailViewModelTest {

    private lateinit var countryDetailViewModel: CountryDetailViewModel
    private lateinit var getCountryDetailUseCase: GetCountryDetailUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        getCountryDetailUseCase = mockk<GetCountryDetailUseCase>()
        countryDetailViewModel = CountryDetailViewModel(getCountryDetailUseCase)
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun `getCountryDetailUseCase updates countryDetails state flow with data from useCase`() =
        runTest {
            //Arrange
            val flow = flowOf(
                UiState.SUCCESS(countryDetailDomainModelTest)
            )

            coEvery { getCountryDetailUseCase.invoke("US") } returns flow

            //Act
            countryDetailViewModel.getCountryDetails("US")
            countryDetailViewModel.countryDetails.drop(1).first()

            //Assert
            assertEquals(
                countryDetailViewModel.countryDetails.value,
                UiState.SUCCESS(countryDetailDomainModelTest)
            )
            coVerify(exactly = 1) { getCountryDetailUseCase.invoke("US") }
        }

    @Test
    fun `getCountryDetailUseCase updates countryDetails state flow with error from useCase`() =
        runTest {
            //Arrange
            val exception = NullResponse("No Country Found with that Code")

            val flow = flowOf(
                UiState.ERROR(exception)
            )

            coEvery { getCountryDetailUseCase.invoke("US") } returns flow

            //Act
            countryDetailViewModel.getCountryDetails("US")
            countryDetailViewModel.countryDetails.drop(1).first()

            //Assert
            assertEquals(countryDetailViewModel.countryDetails.value, UiState.ERROR(exception))
            coVerify(exactly = 1) { getCountryDetailUseCase.invoke("US") }
        }

}