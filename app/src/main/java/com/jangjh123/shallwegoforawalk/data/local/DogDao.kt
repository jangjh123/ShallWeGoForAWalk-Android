package com.jangjh123.shallwegoforawalk.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jangjh123.shallwegoforawalk.data.model.weather.Dog
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDao {
    @Query("SELECT * FROM Dog")
    fun getAllDog(): Flow<List<Dog>>

    @Insert
    suspend fun insertDog(dog: Dog)

    @Query("DELETE FROM Dog WHERE id = :id")
    fun deleteDog(id: Int)
}