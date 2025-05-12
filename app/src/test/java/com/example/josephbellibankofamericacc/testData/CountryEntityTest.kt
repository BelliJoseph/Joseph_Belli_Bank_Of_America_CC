package com.example.josephbellibankofamericacc.testData

import com.example.josephbellibankofamericacc.data.entity.CountryEntity

val singleCountryEntity = CountryEntity(
    code = "US",
    capital = "Washington, D.C.",
    name = "United States of America",
    region = "NA",
    currencyName = "United States dollar",
    currencySymbol = "$",
    languageName = "English"
)

val countryEntityList = listOf(
    CountryEntity(
        code = "AE",
        capital = "Abu Dhabi",
        name = "United Arab Emirates",
        region = "AS",
        currencyName = "United Arab Emirates dirham",
        currencySymbol = "د.إ",
        languageName = "Arabic"
    ),
    CountryEntity(
        code = "GB",
        capital = "London",
        name = "United Kingdom of Great Britain and Northern Ireland",
        region = "EU",
        currencyName = "British pound",
        currencySymbol = "£",
        languageName = "English"
    ),
    CountryEntity(
        code = "US",
        capital = "Washington, D.C.",
        name = "United States of America",
        region = "NA",
        currencyName = "United States dollar",
        currencySymbol = "$",
        languageName = "English"
    )
)