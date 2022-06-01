package com.jangjh123.shallwegoforawalk.data.model.weather

data class WeatherVO(
    val maxTemp: Int,
    val minTemp: Int,
    val fine: Int,
    val uFine: Int,
    val hourlyList: List<HourlyWeather>
)