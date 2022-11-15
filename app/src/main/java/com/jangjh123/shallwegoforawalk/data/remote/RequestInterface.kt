package com.jangjh123.shallwegoforawalk.data.remote

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RequestInterface {
    @GET("forecast.json?")
    fun fetchWeatherData(
        @Query("key") key: String,
        @Query("q") latLng: String,
        @Query("days") days: Int = 2,
        @Query("aqi") airPollution: String = "yes",
        @Query("alerts") alerts: String = "no"
    ): Call<JsonObject>

    @GET("https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?")
    fun fetchAddress(
        @Header("Authorization") key: String,
        @Query("input_coord") input: String,
        @Query("x") longitude: Double,
        @Query("y") latitude: Double
    ): Call<JsonObject>
}