package com.example.josephbellibankofamericacc.data.network


import com.google.gson.annotations.SerializedName

data class CountryNetworkModel(
    @SerializedName("code")
    val code: String,
    @SerializedName("capital")
    val capital: String,
    @SerializedName("currency")
    val currency: Currency,
    @SerializedName("language")
    val language: Language,
    @SerializedName("name")
    val name: String,
    @SerializedName("region")
    val region: String
)