package com.example.josephbellibankofamericacc.di

import android.content.Context
import androidx.room.Room
import com.example.josephbellibankofamericacc.database.AppDatabase
import com.example.josephbellibankofamericacc.database.CountriesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "boa_database"
        ).build()
    }

    @Singleton
    @Provides
    fun providesCountriesDao(db: AppDatabase): CountriesDao = db.countriesDao()
}