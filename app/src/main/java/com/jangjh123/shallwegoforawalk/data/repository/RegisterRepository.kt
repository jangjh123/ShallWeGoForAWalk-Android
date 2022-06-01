package com.jangjh123.shallwegoforawalk.data.repository

import com.jangjh123.shallwegoforawalk.data.local.DogDao
import com.jangjh123.shallwegoforawalk.data.model.DogListTypes
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val dogDao: DogDao
) {
    suspend fun storeIntoRoom(dog: DogListTypes.Dog) {
        dogDao.insertDog(dog)
    }
}