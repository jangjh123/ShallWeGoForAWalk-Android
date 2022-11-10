package com.jangjh123.shallwegoforawalk.data.repository

import com.jangjh123.shallwegoforawalk.data.local.DogDao
import com.jangjh123.shallwegoforawalk.data.model.weather.Dog
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val dogDao: DogDao
) {
    suspend fun storeIntoRoom(dog: Dog) {
        dogDao.insertDog(dog)
    }
}