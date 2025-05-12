package com.example.josephbellibankofamericacc.data.util

import com.example.josephbellibankofamericacc.data.domain.CountryDetailDomainModel
import com.example.josephbellibankofamericacc.data.domain.CountryListDomainModel
import com.example.josephbellibankofamericacc.data.entity.CountryEntity
import com.example.josephbellibankofamericacc.data.network.CountryNetworkModel

fun List<CountryNetworkModel>.toEntityList(): List<CountryEntity> {
    return this.map {
        CountryEntity(
            code = it.code,
            capital = it.capital,
            name = it.name,
            region = it.region,
            currencyName = it.currency.name,
            currencySymbol = it.currency.symbol.orEmpty(),
            languageName = it.language.name
        )
    }
}

fun List<CountryEntity>.toDomainList(): List<CountryListDomainModel> {
    return this.map {
        CountryListDomainModel(
            code = it.code,
            capital = it.capital,
            name = it.name,
            region = it.region
        )
    }
}

fun CountryEntity.toDomainDetail(): CountryDetailDomainModel =
    CountryDetailDomainModel(
        code = this.code,
        capital = this.capital,
        name = this.name,
        region = this.region,
        currencyName = this.currencyName,
        currencySymbol = this.currencySymbol,
        languageName = this.languageName
    )