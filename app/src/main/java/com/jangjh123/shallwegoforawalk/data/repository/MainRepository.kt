package com.jangjh123.shallwegoforawalk.data.repository

import com.jangjh123.shallwegoforawalk.data.local.DogDao
import com.jangjh123.shallwegoforawalk.data.model.Dog
import javax.inject.Inject

class MainRepository @Inject constructor(private val dogDao: DogDao) {
    suspend fun getDogList() : List<Dog> {
        return dogDao.getAllDog()
    }
}