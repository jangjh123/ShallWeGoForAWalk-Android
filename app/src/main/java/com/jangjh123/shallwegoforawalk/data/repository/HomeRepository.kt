package com.jangjh123.shallwegoforawalk.data.repository

import com.jangjh123.shallwegoforawalk.data.local.DogDao
import com.jangjh123.shallwegoforawalk.data.remote.DataSource
import com.jangjh123.shallwegoforawalk.util.CoroutineScopes
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val dogDao: DogDao,
    private val dataSource: DataSource
) {
    fun fetchDogs() {
        CoroutineScopes.io {
            dogDao.getAllDog().collect {

            }
        }
    }

    fun fetchWeather() {
        CoroutineScopes.io {
//            dataSource.fetchWeatherData()
        }
    }
}