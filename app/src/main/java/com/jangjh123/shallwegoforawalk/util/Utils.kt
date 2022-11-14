package com.jangjh123.shallwegoforawalk.util

import com.jangjh123.shallwegoforawalk.data.model.weather.WeatherVO

object Utils {
    fun convertKelvinToCelsius(kelvinTemp: Int) = (kelvinTemp - 273.15f).toInt()
}
