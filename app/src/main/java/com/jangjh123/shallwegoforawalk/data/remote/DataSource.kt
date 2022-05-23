package com.jangjh123.shallwegoforawalk.data.remote

import com.google.gson.JsonObject
import com.jangjh123.shallwegoforawalk.BuildConfig
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class DataSource {
    companion object {
        private val apiService = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .build()
            )
            .build().create(RequestInterface::class.java)
    }

    fun fetchWeatherData(lat: Float, lon: Float): Single<JsonObject> =
        apiService.fetchWeatherData(
            lat,
            lon,
            "minutely",
            BuildConfig.KEY_API
        )

    fun fetchDustData(lat: Float, lon: Float): Single<JsonObject> =
        apiService.fetchDustData(
            lat,
            lon,
            BuildConfig.KEY_API
        )
}


