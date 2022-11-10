package com.jangjh123.shallwegoforawalk.data.repository

import com.google.gson.JsonObject
import com.jangjh123.shallwegoforawalk.data.local.DogDao
import com.jangjh123.shallwegoforawalk.data.model.weather.HourlyWeather
import com.jangjh123.shallwegoforawalk.data.model.weather.WeatherVO
import com.jangjh123.shallwegoforawalk.data.remote.DataSource
import kotlinx.coroutines.flow.transform
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

class HomeRepository @Inject constructor(
    private val dogDao: DogDao,
    private val dataSource: DataSource
) {
    fun fetchDogs() = dogDao.getAllDog()
    fun fetchWeather(coordinate: String) =
        dataSource.fetchWeatherData(coordinate).transform { response ->
            when (response) {
                is JsonObject -> {
                    emit(parseWeather(response))
                }
                is String -> {
                    emit(response)
                }
            }
        }


    private fun parseWeather(weather: JsonObject): WeatherVO {
        val temperature =
            weather["forecast"].asJsonObject["forecastday"].asJsonArray[0].asJsonObject["day"].asJsonObject
        val dust = weather["current"].asJsonObject["air_quality"].asJsonObject
        val todayArray =
            weather["forecast"].asJsonObject["forecastday"].asJsonArray[0].asJsonObject["hour"].asJsonArray
        val tomorrowArray =
            weather["forecast"].asJsonObject["forecastday"].asJsonArray[1].asJsonObject["hour"].asJsonArray
        val forecastList = ArrayList<HourlyWeather>()
        val curTime = SimpleDateFormat("HH").apply {
            timeZone = TimeZone.getTimeZone("Asia/Seoul")
        }.format(Date(System.currentTimeMillis())).toInt()
        for (i in 0..6) {
            val forecast = if (curTime + i < 24) {
                todayArray[curTime + i].asJsonObject
            } else {
                tomorrowArray[curTime + i - 24].asJsonObject
            }
            forecastList.add(
                HourlyWeather(
                    temp = forecast["temp_c"].asFloat.roundToInt(),
                    humidity = forecast["humidity"].asInt,
                    pop = forecast["chance_of_rain"].asInt,
                    windSpeed = forecast["wind_mph"].asFloat.roundToInt(),
                    icon = "https:${forecast["condition"].asJsonObject["icon"].asString}"
                )
            )
        }

        return WeatherVO(
            maxTemp = temperature["maxtemp_c"].asInt,
            minTemp = temperature["mintemp_c"].asInt,
            fine = if (dust["pm10"] != null) {
                dust["pm10"].asFloat.roundToInt()
            } else {
                0
            },
            uFine = if (dust["pm2_5"] != null) {
                dust["pm2_5"].asFloat.roundToInt()
            } else {
                0
            },
            hourlyList = forecastList
        )
    }
}