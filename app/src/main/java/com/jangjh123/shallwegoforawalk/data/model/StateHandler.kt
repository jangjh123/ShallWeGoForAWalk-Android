package com.jangjh123.shallwegoforawalk.data.model

import com.jangjh123.shallwegoforawalk.data.model.weather.WeatherVO

sealed class WeatherStateHandler {
    data class Success(val data: WeatherVO) : WeatherStateHandler()
    data class Failure(val errorMessage: String) : WeatherStateHandler()
}