package com.jangjh123.shallwegoforawalk.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.JsonObject
import com.jangjh123.data_store.KEY_ADDRESS_LATITUDE
import com.jangjh123.data_store.KEY_ADDRESS_LONGITUDE
import com.jangjh123.shallwegoforawalk.data.local.DogDao
import com.jangjh123.shallwegoforawalk.data.model.weather.AddressVO
import com.jangjh123.shallwegoforawalk.data.model.weather.HourlyWeather
import com.jangjh123.shallwegoforawalk.data.model.weather.WeatherVO
import com.jangjh123.shallwegoforawalk.data.remote.DataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

class HomeRepository @Inject constructor(
    private val dogDao: DogDao,
    private val dataSource: DataSource,
    private val dataStore: DataStore<Preferences>
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

    fun fetchAddress(latitude: Double, longitude: Double) =
        dataSource.fetchAddress(latitude, longitude).transform { response ->
            when (response) {
                is JsonObject -> {
                    emit(parseAddress(response))
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
        val curTime = SimpleDateFormat("HH").apply { timeZone = TimeZone.getTimeZone("Asia/Seoul") }
            .format(Date(System.currentTimeMillis())).toInt()
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

    private fun parseAddress(address: JsonObject): AddressVO {
        val location = address.get("documents").asJsonArray.get(0).asJsonObject
        val sb = StringBuilder().apply {
            append(location.get("region_1depth_name").asString)
            append(" ")
            append(location.get("region_2depth_name").asString)
        }
        return AddressVO(sb.toString().replace("\"", ""))
    }

    suspend fun storeCoordinate(latitude: Double, longitude: Double) {
        dataStore.edit {
            it[KEY_ADDRESS_LATITUDE] = latitude
            it[KEY_ADDRESS_LONGITUDE] = longitude
        }
    }

    fun loadStoredCoordinate() = combineTransform(
        dataStore.data.map { it[KEY_ADDRESS_LATITUDE] ?: 0 }.flowOn(Dispatchers.IO),
        dataStore.data.map { it[KEY_ADDRESS_LONGITUDE] ?: 0 }.flowOn(Dispatchers.IO),
    ) { latitude, longitude ->
        emit(Pair(latitude, longitude))
    }
}