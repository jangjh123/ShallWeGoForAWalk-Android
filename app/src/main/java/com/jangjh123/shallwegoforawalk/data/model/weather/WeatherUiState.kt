package com.jangjh123.shallwegoforawalk.data.model.weather

sealed class WeatherUiState {
    data class Success(val data: WeatherVO) : WeatherUiState()
    data class Failure(val errorMessage: String) : WeatherUiState()
}