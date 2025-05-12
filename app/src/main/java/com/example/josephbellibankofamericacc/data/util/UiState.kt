package com.example.josephbellibankofamericacc.data.util

sealed class UiState<out T> {
    data object LOADING : UiState<Nothing>()
    data class SUCCESS<T>(val response: T) : UiState<T>()
    data class ERROR(val error: Exception) : UiState<Nothing>()
}