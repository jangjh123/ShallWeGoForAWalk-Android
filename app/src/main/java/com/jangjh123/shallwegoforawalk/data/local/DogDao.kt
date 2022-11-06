package com.jangjh123.shallwegoforawalk.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jangjh123.shallwegoforawalk.data.model.DogListTypes
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDao {
    @Query("SELECT * FROM Dog")
    fun getAllDog(): Flow<DogListTypes.Dog>

    @Insert
    fun insertDog(dog: DogListTypes.Dog)

    @Query("DELETE FROM Dog WHERE id = :id")
    fun deleteDog(id: Int)
}