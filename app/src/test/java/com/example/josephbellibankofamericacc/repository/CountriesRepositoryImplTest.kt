package com.example.josephbellibankofamericacc.repository

import com.example.josephbellibankofamericacc.data.network.CountryNetworkModel
import com.example.josephbellibankofamericacc.data.repository.CountriesRepositoryImpl
import com.example.josephbellibankofamericacc.data.service.CountriesService
import com.example.josephbellibankofamericacc.database.CountriesDao
import com.example.josephbellibankofamericacc.testData.countryEntityListTest
import com.example.josephbellibankofamericacc.testData.countryNetworkListTest
import com.example.josephbellibankofamericacc.testData.singleCountryEntityTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CountriesRepositoryImplTest {

    private lateinit var countriesRepositoryImpl: CountriesRepositoryImpl
    private lateinit var countriesService: CountriesService
    private lateinit var countriesDao: CountriesDao

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        countriesService = mockk<CountriesService>()
        countriesDao = mockk<CountriesDao>()
        countriesRepositoryImpl = CountriesRepositoryImpl(
            service = countriesService,
            countriesDao = countriesDao
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun `getNetworkCountries returns successful response from network`() = runTest {
        //Arrange
        val networkResponse = Response.success(countryNetworkListTest)
        coEvery { countriesService.callCountriesApi() } returns networkResponse

        //Act
        val response = countriesRepositoryImpl.getNetworkCountries()

        //Assert
        assertEquals(response, networkResponse)
        coVerify(exactly = 1) { countriesService.callCountriesApi() }
    }

    @Test
    fun `getNetworkCountries returns error response from network`() = runTest {
        //Arrange
        val errorMessage = "Network error"
        val networkResponse =
            Response.error<List<CountryNetworkModel>>(400, errorMessage.toResponseBody())
        coEvery { countriesService.callCountriesApi() } returns networkResponse

        //Act
        val response = countriesRepositoryImpl.getNetworkCountries()

        //Assert
        assertEquals(response, networkResponse)
        coVerify(exactly = 1) { countriesService.callCountriesApi() }
    }

    @Test
    fun `test insertAllLocalCountries`() = runTest {
        //Arrange
        val insertCountries = countryEntityListTest
        coEvery { countriesDao.insertAllCountries(insertCountries) } returns Unit

        //Act
        countriesRepositoryImpl.insertAllLocalCountries(insertCountries)

        //Assert
        coVerify(exactly = 1) { countriesDao.insertAllCountries(insertCountries) }
    }

    @Test
    fun `getLocalCountryDetail with data present in DB`() = runTest {
        //Arrange
        val entity = singleCountryEntityTest
        coEvery { countriesDao.getCountry("US") } returns entity

        //Act
        val response = countriesRepositoryImpl.getLocalCountryDetail("US")

        //Assert
        assertEquals(response, entity)
        coVerify(exactly = 1) { countriesDao.getCountry("US") }
    }

    @Test
    fun `getLocalCountryDetail with NO data present in DB`() = runTest {
        //Arrange
        coEvery { countriesDao.getCountry("US") } returns null

        //Act
        val response = countriesRepositoryImpl.getLocalCountryDetail("US")

        //Assert
        assertEquals(response, null)
        coVerify(exactly = 1) { countriesDao.getCountry("US") }
    }

    @Test
    fun `getLocalCountryList with data present in the DB`() = runTest {
        //Arrange
        val entityList = countryEntityListTest
        coEvery { countriesDao.getAllCountries() } returns entityList

        //Act
        val response = countriesRepositoryImpl.getLocalCountryList()

        //Assert
        assertEquals(response, entityList)
        coVerify(exactly = 1) { countriesDao.getAllCountries() }
    }

    @Test
    fun `getLocalCountryList with data NOT present in the DB`() = runTest {
        //Arrange
        coEvery { countriesDao.getAllCountries() } returns emptyList()

        //Act
        val response = countriesRepositoryImpl.getLocalCountryList()

        //Assert
        assertEquals(response, emptyList())
        coVerify(exactly = 1) { countriesDao.getAllCountries() }
    }
}