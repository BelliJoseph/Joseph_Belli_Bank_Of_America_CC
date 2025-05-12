package com.example.josephbellibankofamericacc.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country_table")
data class CountryEntity(
    @PrimaryKey
    val code: String,
    val capital: String,
    val name: String,
    val region: String,
    @ColumnInfo(name = "currency_name")
    val currencyName: String,
    @ColumnInfo(name = "currency_symbol")
    val currencySymbol: String,
    @ColumnInfo(name = "language_name")
    val languageName: String
)
