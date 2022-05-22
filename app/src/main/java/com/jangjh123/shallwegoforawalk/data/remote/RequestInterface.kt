package com.jangjh123.shallwegoforawalk.data.remote

import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestInterface {
    @GET("onecall?")
    fun fetchWeatherData(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float,
        @Query("exclude") exclude: String,
        @Query("appid", encoded = true) auth: String
    ): Single<JsonObject>

    @GET("air_pollution?")
    fun fetchDustData(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float,
        @Query("appid", encoded = true) auth: String
    ): Single<JsonObject>
}