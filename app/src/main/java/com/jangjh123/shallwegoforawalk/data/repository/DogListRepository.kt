package com.jangjh123.shallwegoforawalk.data.repository

import com.jangjh123.shallwegoforawalk.data.local.DogDao
import javax.inject.Inject

class DogListRepository @Inject constructor(
    private val dogDao: DogDao
) {
//    suspend fun readDogList(): List<DogListTypes.Dog> =
//        dogDao.getAllDog()
//
//    suspend fun removeDogById(id: Int) {
//        dogDao.deleteDog(id)
//    }
}