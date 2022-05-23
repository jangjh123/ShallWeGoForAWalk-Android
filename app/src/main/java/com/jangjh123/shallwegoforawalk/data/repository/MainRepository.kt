package com.jangjh123.shallwegoforawalk.data.repository

import com.jangjh123.shallwegoforawalk.data.local.DogDao
import com.jangjh123.shallwegoforawalk.data.model.DogListTypes
import com.jangjh123.shallwegoforawalk.data.remote.DataSource
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val dogDao: DogDao,
    private val dataSource: DataSource
) {
    suspend fun getDogList(): List<DogListTypes.Dog> {
        return dogDao.getAllDog()
    }

    fun fetchWeatherInfo(lat: Float, lon: Float) =
        listOf(dataSource.fetchWeatherData(lat, lon), dataSource.fetchDustData(lat, lon))
}