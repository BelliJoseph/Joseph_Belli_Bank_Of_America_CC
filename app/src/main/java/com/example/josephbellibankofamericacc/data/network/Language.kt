package com.example.josephbellibankofamericacc.data.network


import com.google.gson.annotations.SerializedName

data class Language(
    @SerializedName("code")
    val code: String,
    @SerializedName("iso639_2")
    val iso6392: String? = null,
    @SerializedName("name")
    val name: String,
    @SerializedName("nativeName")
    val nativeName: String? = null
)