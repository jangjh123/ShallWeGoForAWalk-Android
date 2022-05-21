package com.jangjh123.shallwegoforawalk.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jangjh123.shallwegoforawalk.data.model.DogListTypes

@Dao
interface DogDao {
    @Query("SELECT * FROM Dog")
    suspend fun getAllDog(): List<DogListTypes.Dog>

    @Insert
    suspend fun insertDog(dog: DogListTypes.Dog)

    @Query("DELETE FROM Dog WHERE id = :id")
    suspend fun deleteDog(id: Int)
}