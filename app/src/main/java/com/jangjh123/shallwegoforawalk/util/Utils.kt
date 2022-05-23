package com.jangjh123.shallwegoforawalk.util

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun convertKelvinToCelsius(kelvinTemp: Int) = (kelvinTemp - 273.15f).toInt()
}