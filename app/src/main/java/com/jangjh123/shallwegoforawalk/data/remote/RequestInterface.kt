package com.jangjh123.shallwegoforawalk.data.remote

import com.google.gson.JsonObject
import com.jangjh123.shallwegoforawalk.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
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

    @Headers("Authorization: KakaoAK " + BuildConfig.KEY_KAKAO)
    @GET("https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?")
    fun fetchAddress(
        @Query("input_coord") input: String,
        @Query("x") longitude: Double,
        @Query("y") latitude: Double
    ): Call<JsonObject>
}