package com.jangjh123.shallwegoforawalk.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jangjh123.shallwegoforawalk.data.model.weather.Dog

@Database(entities = [Dog::class], version = 1, exportSchema = false)
abstract class DogDatabase : RoomDatabase() {

    abstract fun getDogDao(): DogDao

    companion object {
        const val DATABASE_NAME: String = "dog_db"
    }
}