package com.jangjh123.shallwegoforawalk.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jangjh123.shallwegoforawalk.data.model.Dog

@Dao
interface DogDao {
    @Query("SELECT * FROM Dog")
    suspend fun getAllDog(): List<Dog>

    @Insert
    suspend fun insertDog(dog: Dog)

    @Query("DELETE FROM Dog WHERE id = :id")
    suspend fun deleteDog(id: Int)
}