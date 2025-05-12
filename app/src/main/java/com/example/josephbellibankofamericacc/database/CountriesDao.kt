package com.example.josephbellibankofamericacc.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.josephbellibankofamericacc.data.entity.CountryEntity

@Dao
interface CountriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCountries(countries: List<CountryEntity>)

    @Query("SELECT * FROM country_table")
    suspend fun getAllCountries(): List<CountryEntity>

    @Query("SELECT * FROM country_table WHERE code LIKE :countryCode")
    suspend fun getCountry(
        countryCode: String,
    ): CountryEntity

}