package com.jangjh123.shallwegoforawalk.data.repository

import com.jangjh123.shallwegoforawalk.data.local.DogDao
import javax.inject.Inject

class SplashRepository @Inject constructor(
    private val dogDao: DogDao
) {

    suspend fun getDogList() = dogDao.getAllDog()
}