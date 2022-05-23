package com.jangjh123.shallwegoforawalk.data.model.weather

data class WeatherVO(
    val hourlyWeatherList: List<HourlyWeather>,
    val maxTemp: Int,
    val minTemp: Int,
    val fineDust: Int,
    val ultraFineDust: Int
)