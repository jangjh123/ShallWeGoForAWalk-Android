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
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
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

    fun getWeatherData(lat: Float, lon: Float) {
        val data = repository.fetchWeatherInfo(lat, lon)
        val disposable = Single.zip(data[0], data[1]) { weather, dust ->
            WeatherVO(
                hourlyWeatherList = createHourlyWeatherList(weather.get("hourly").asJsonArray),
                maxTemp =
                convertKelvinToCelsius(
                    weather.get("daily").asJsonArray[0].asJsonObject.get("temp").asJsonObject.get(
                        "max"
                    ).asInt
                ),
                minTemp = convertKelvinToCelsius(
                    weather.get("daily").asJsonArray[0].asJsonObject.get(
                        "temp"
                    ).asJsonObject.get("min").asInt
                ),
                fineDust = dust.get("list").asJsonArray[0].asJsonObject.get("components").asJsonObject.get(
                    "pm10"
                ).asInt,
                ultraFineDust = dust.get("list").asJsonArray[0].asJsonObject.get("components").asJsonObject.get(
                    "pm2_5"
                ).asInt
            )
        }
            .applySchedulers()
            .doOnError {
                // 에러 발생했을 때. 네트웍 미 연결 등
                Log.d("TEST", "doOnError")
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
            },
                { throwable ->
                    Log.d("Exception", throwable.toString())
                }
            )
        addDisposable(disposable)
    }

    private fun createHourlyWeatherList(weatherJson: JsonArray) =
        mutableListOf<HourlyWeather>().apply {
            for (i in 0..6) {
                with(weatherJson[i].asJsonObject) {
                    this@apply.add(
                        HourlyWeather(
                            temp = convertKelvinToCelsius(this.get("temp").asInt),
                            humidity = this.get("humidity").asInt,
                            pop = (this.get("pop").asFloat * 100).toInt(),
                            windSpeed = this.get("wind_speed").asFloat,
                            icon = this.get("weather").asJsonArray[0].asJsonObject.get("icon").asString
                        )
                    )
                }
            }
        }
}
