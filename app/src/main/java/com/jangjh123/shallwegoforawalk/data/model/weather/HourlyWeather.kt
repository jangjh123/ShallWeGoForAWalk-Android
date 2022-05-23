package com.jangjh123.shallwegoforawalk.data.model.weather

data class HourlyWeather(
    val temp: Int,
    val humidity: Int,
    val pop: Int,
    val windSpeed: Float,
    val icon: String
)
