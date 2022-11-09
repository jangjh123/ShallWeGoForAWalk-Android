package com.jangjh123.shallwegoforawalk.data.remote

import android.util.Log
import com.google.gson.JsonObject
import com.jangjh123.shallwegoforawalk.BuildConfig
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class DataSource {
    companion object {
        private val apiService = Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(3, TimeUnit.SECONDS)
                    .build()
            )
            .build().create(RequestInterface::class.java)
    }

    fun fetchWeatherData(coordinate: String) = callbackFlow {
        apiService.fetchWeatherData(
            BuildConfig.KEY_WEATHER,
            coordinate
        ).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                Log.d("response", call.request().url.toString())
                trySend(
                    if (response.code() == 200) {
                        trySend(response.body())
                    } else {
                        trySend("Success but error occurred. ${response.body()}")
                    }
                )
                close()
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                trySend(t.message)
                close()
            }
        })
        awaitClose()
    }

    fun fetchAddress(longitude: Double, latitude: Double) =
        apiService.fetchAddress(BuildConfig.KEY_KAKAO, longitude, latitude)
}



