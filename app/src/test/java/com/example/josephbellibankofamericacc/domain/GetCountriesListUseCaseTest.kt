package com.example.josephbellibankofamericacc.domain

import com.example.josephbellibankofamericacc.data.network.CountryNetworkModel
import com.example.josephbellibankofamericacc.data.util.UiState
import com.example.josephbellibankofamericacc.data.util.toDomainList
import com.example.josephbellibankofamericacc.data.util.toEntityList
import com.example.josephbellibankofamericacc.testData.countryEntityListTest
import com.example.josephbellibankofamericacc.testData.countryNetworkListTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class GetCountriesListUseCaseTest {

    private lateinit var getCountriesListUseCase: GetCountriesListUseCase
    private lateinit var repository: CountriesRepository
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        repository = mockk<CountriesRepository>()
        getCountriesListUseCase = GetCountriesListUseCase(repository, dispatcher)
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke emits LOADING state first`() = runTest {
        //Arrange
        coEvery { repository.getLocalCountryList() } returns countryEntityListTest

        //Act
        val flow = getCountriesListUseCase.invoke()
        val firstEmission = flow.first()

        //Assert
        assertEquals(UiState.LOADING, firstEmission)
    }

    @Test
    fun `invoke SUCCESS when data present in DB`() = runTest {
        //Arrange
        coEvery { repository.getLocalCountryList() } returns countryEntityListTest

        //Act
        val flow = getCountriesListUseCase.invoke()
        val emission = flow.toList()

        //Assert
        assert(emission[0] is UiState.LOADING)
        assertEquals(emission[1], UiState.SUCCESS(countryEntityListTest.toDomainList()))
        coVerify(exactly = 1) { repository.getLocalCountryList() }
        coVerify(exactly = 0) { repository.getNetworkCountries() }
    }

    @Test
    fun `invoke ERROR when data not present in DB and network NOT successful`() = runTest {
        //Arrange
        val errorMessage = "Error message"
        val networkResponse =
            Response.error<List<CountryNetworkModel>>(400, errorMessage.toResponseBody())
        coEvery { repository.getLocalCountryList() } returns emptyList()
        coEvery { repository.getNetworkCountries() } returns networkResponse

        //Act
        val flow = getCountriesListUseCase.invoke()
        val emission = flow.toList()

        //Assert
        assert(emission[0] is UiState.LOADING)
        assert(emission[1] is UiState.ERROR)
        coVerify(exactly = 1) { repository.getLocalCountryList() }
        coVerify(exactly = 1) { repository.getNetworkCountries() }
        coVerify(exactly = 0) { repository.insertAllLocalCountries(emptyList()) }
    }

    @Test
    fun `invoke ERROR when data not present in DB and network successful but empty body`() =
        runTest {
            //Arrange
            coEvery { repository.getLocalCountryList() } returns emptyList()
            coEvery { repository.getNetworkCountries() } returns Response.success(null)

            //Act
            val flow = getCountriesListUseCase.invoke()
            val emission = flow.toList()

            //Assert
            assert(emission[0] is UiState.LOADING)
            assert(emission[1] is UiState.ERROR)
            coVerify(exactly = 1) { repository.getLocalCountryList() }
            coVerify(exactly = 1) { repository.getNetworkCountries() }
            coVerify(exactly = 0) { repository.insertAllLocalCountries(emptyList()) }
        }

    @Test
    fun `invoke SUCCESS when data not present in DB and network successful`() = runTest {
        //Arrange
        val entityList = countryNetworkListTest.toEntityList()
        val networkResponse = Response.success(countryNetworkListTest)
        coEvery { repository.getLocalCountryList() } returnsMany listOf(emptyList(), entityList)
        coEvery { repository.getNetworkCountries() } returns networkResponse
        coJustRun { repository.insertAllLocalCountries(entityList) }

        //Act
        val flow = getCountriesListUseCase.invoke()
        val emission = flow.toList()

        //Assert
        assert(emission[0] is UiState.LOADING)
        assertEquals(emission[1], UiState.SUCCESS(entityList.toDomainList()))
        coVerify(exactly = 2) { repository.getLocalCountryList() }
        coVerify(exactly = 1) { repository.getNetworkCountries() }
        coVerify(exactly = 1) { repository.insertAllLocalCountries(entityList) }
    }

    @Test
    fun `invoke ERROR when data not present in DB and network successful but fails to store in DB`() =
        runTest {
            //Arrange
            val entityList = countryNetworkListTest.toEntityList()
            val networkResponse = Response.success(countryNetworkListTest)
            coEvery { repository.getLocalCountryList() } returnsMany listOf(
                emptyList(),
                emptyList()
            )
            coEvery { repository.getNetworkCountries() } returns networkResponse
            coJustRun { repository.insertAllLocalCountries(entityList) }

            //Act
            val flow = getCountriesListUseCase.invoke()
            val emission = flow.toList()

            //Assert
            assert(emission[0] is UiState.LOADING)
            assert(emission[1] is UiState.ERROR)
            coVerify(exactly = 2) { repository.getLocalCountryList() }
            coVerify(exactly = 1) { repository.getNetworkCountries() }
            coVerify(exactly = 1) { repository.insertAllLocalCountries(entityList) }
        }

}