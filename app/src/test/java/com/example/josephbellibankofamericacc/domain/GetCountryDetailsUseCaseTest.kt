package com.example.josephbellibankofamericacc.domain

import com.example.josephbellibankofamericacc.data.util.UiState
import com.example.josephbellibankofamericacc.data.util.toDomainDetail
import com.example.josephbellibankofamericacc.testData.singleCountryEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetCountryDetailsUseCaseTest {

    private lateinit var getCountryDetailUseCase: GetCountryDetailUseCase
    private lateinit var repository: CountriesRepository
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        repository = mockk<CountriesRepository>()
        getCountryDetailUseCase = GetCountryDetailUseCase(repository, dispatcher)
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke emits LOADING state first`() = runTest {
        //Arrange
        coEvery { repository.getLocalCountryDetail("US") } returns singleCountryEntity

        //Act
        val flow = getCountryDetailUseCase.invoke(code = "US")
        val firstEmission = flow.first()

        //Assert
        assertEquals(UiState.LOADING, firstEmission)

    }

    @Test
    fun `invoke emits SUCCESS state when entity present in DB`() = runTest {
        //Arrange
        coEvery { repository.getLocalCountryDetail("US") } returns singleCountryEntity

        //Act
        val flow = getCountryDetailUseCase.invoke("US")
        val emission = flow.toList()

        //Assert
        assert(emission[0] is UiState.LOADING)
        assertEquals(emission[1], UiState.SUCCESS(singleCountryEntity.toDomainDetail()))
        coVerify(exactly = 1) { repository.getLocalCountryDetail("US") }
    }


    @Test
    fun `invoke emits ERROR state when entity NOT present in DB`() = runTest {
        //Arrange
        coEvery { repository.getLocalCountryDetail("GB") } returns null

        //Act
        val flow = getCountryDetailUseCase.invoke("GB")
        val emission = flow.toList()

        //Assert
        assert(emission[0] is UiState.LOADING)
        assert(emission[1] is UiState.ERROR)
        coVerify(exactly = 1) { repository.getLocalCountryDetail("GB") }
    }
}