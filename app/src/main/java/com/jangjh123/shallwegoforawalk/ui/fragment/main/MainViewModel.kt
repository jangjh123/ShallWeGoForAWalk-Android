package com.jangjh123.shallwegoforawalk.ui.fragment.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonArray
import com.jangjh123.shallwegoforawalk.data.model.DogListTypes.Dog
import com.jangjh123.shallwegoforawalk.data.model.weather.HourlyWeather
import com.jangjh123.shallwegoforawalk.data.model.weather.WeatherVO
import com.jangjh123.shallwegoforawalk.data.repository.MainRepository
import com.jangjh123.shallwegoforawalk.ui.base.BaseViewModel
import com.jangjh123.shallwegoforawalk.util.Utils.convertKelvinToCelsius
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    dispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _dogList = MutableLiveData<List<Dog>>()
    val dogList: LiveData<List<Dog>>
        get() = _dogList

    private val _weatherData = MutableLiveData<WeatherVO>()
    val weatherData: LiveData<WeatherVO>
        get() = _weatherData

    init {
        CoroutineScope(dispatcher).launch {
            _dogList.postValue(repository.getDogList())
        }
    }

    fun getWeatherData(lat: Double, lon: Double, onError:() -> Unit) {
        val disposable = repository.fetchWeatherData("$lat,$lon")
            .map {
                val temp =
                    it.get("forecast").asJsonObject.get("forecastday").asJsonArray[0].asJsonObject.get(
                        "day"
                    ).asJsonObject
                val dust = it.get("current").asJsonObject.get("air_quality").asJsonObject

                val todayList =
                    it.get("forecast").asJsonObject.get("forecastday").asJsonArray[0].asJsonObject.get(
                        "hour"
                    ).asJsonArray
                val tomorrowList =
                    it.get("forecast").asJsonObject.get("forecastday").asJsonArray[1].asJsonObject.get(
                        "hour"
                    ).asJsonArray
                val forecastList = ArrayList<HourlyWeather>()

                val curTime = SimpleDateFormat("HH").apply {
                    timeZone = TimeZone.getTimeZone("Asia/Seoul")
                }.format(
                    Date(System.currentTimeMillis())
                ).toInt()

                for (i in 0..6) {
                    if (curTime + i < 24) {
                        val forecast = todayList[curTime + i].asJsonObject
                        forecastList.add(
                            HourlyWeather(
                                temp = forecast.get("temp_c").asFloat.toInt(),
                                humidity = forecast.get("humidity").asInt,
                                pop = forecast.get("chance_of_rain").asInt,
                                windSpeed = forecast.get("wind_mph").asFloat.toInt(),
                                icon = "https:${forecast.get("condition").asJsonObject.get("icon").asString}"
                            )
                        )
                    } else {
                        val forecast = tomorrowList[curTime + i - 24].asJsonObject
                        forecastList.add(
                            HourlyWeather(
                                temp = forecast.get("temp_c").asFloat.toInt(),
                                humidity = forecast.get("humidity").asInt,
                                pop = forecast.get("chance_of_rain").asInt,
                                windSpeed = forecast.get("wind_mph").asFloat.toInt(),
                                icon = "https:${forecast.get("condition").asJsonObject.get("icon").asString}"
                            )
                        )
                    }
                }

                WeatherVO(
                    maxTemp = temp.get("maxtemp_c").asInt,
                    minTemp = temp.get("mintemp_c").asInt,
                    fine = dust.get("pm10").asFloat.toInt(),
                    uFine = dust.get("pm2_5").asFloat.toInt(),
                    hourlyList = forecastList
                )
            }.doOnError {
                onError()
            }
            .retryWhen { attempts ->
                attempts.zipWith(
                    Flowable.range(1, 3)
                ) { _, t2 -> t2 }.flatMap {
                    Flowable.timer(5, TimeUnit.SECONDS)
                }
            }
            .subscribe({ data ->
                _weatherData.postValue(data)
            }, {
                onError()
            })

        addDisposable(disposable)
    }
}
