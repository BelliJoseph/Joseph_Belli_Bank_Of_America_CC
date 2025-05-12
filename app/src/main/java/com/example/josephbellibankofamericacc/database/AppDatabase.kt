package com.example.josephbellibankofamericacc.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.josephbellibankofamericacc.data.entity.CountryEntity

@Database(
    entities = [CountryEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countriesDao(): CountriesDao
}