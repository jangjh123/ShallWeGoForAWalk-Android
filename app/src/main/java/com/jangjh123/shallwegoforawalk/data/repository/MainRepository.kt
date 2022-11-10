package com.jangjh123.shallwegoforawalk.data.repository

import com.jangjh123.shallwegoforawalk.data.local.DogDao
import com.jangjh123.shallwegoforawalk.data.remote.DataSource
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val dogDao: DogDao,
    private val dataSource: DataSource
) {
//    suspend fun getDogList(): List<DogListTypes.Dog> {
//        return dogDao.getAllDog()
//    }
//
//    fun fetchWeatherData(latLon: String) =
//        dataSource.fetchWeatherData(latLon)
//
//    fun fetchAddress(longitude: Double, latitude: Double) {
//        dataSource.fetchAddress(longitude, latitude).enqueue(object : Callback<JsonObject> {
//            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
//
//            }
//
//            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
//
//            }
//        })
//    }
}