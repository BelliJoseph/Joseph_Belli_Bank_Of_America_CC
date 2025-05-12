package com.example.josephbellibankofamericacc.testData

import com.example.josephbellibankofamericacc.data.network.CountryNetworkModel
import com.example.josephbellibankofamericacc.data.network.Currency
import com.example.josephbellibankofamericacc.data.network.Language

val countryNetworkListTest = listOf(
    CountryNetworkModel(
        code = "UA",
        capital = "Kiev",
        currency = Currency(
            code = "UAH",
            name = "Ukrainian hryvnia",
            symbol = "₴"
        ),
        language = Language(
            code = "uk",
            iso6392 = "",
            name = "Ukrainian",
            nativeName = ""
        ),
        name = "Ukraine",
        region = "EU"
    ),
    CountryNetworkModel(
        code = "GB",
        capital = "London",
        currency = Currency(
            code = "GBP",
            name = "British pound",
            symbol = "£"
        ),
        language = Language(
            code = "en",
            iso6392 = "",
            name = "English",
            nativeName = ""
        ),
        name = "United Kingdom of Great Britain and Northern Ireland",
        region = "EU"
    ),
    CountryNetworkModel(
        code = "US",
        capital = "Washington, D.C.",
        currency = Currency(
            code = "USD",
            name = "United States dollar",
            symbol = "$"
        ),
        language = Language(
            code = "en",
            iso6392 = "eng",
            name = "English",
            nativeName = "English"
        ),
        name = "United States of America",
        region = "NA"
    ),

)