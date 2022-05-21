package com.jangjh123.shallwegoforawalk.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.jangjh123.data_store.KEY_IS_REGISTERED
import com.jangjh123.shallwegoforawalk.data.local.DogDao
import com.jangjh123.shallwegoforawalk.data.model.Dog
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val dogDao: DogDao,
    private val dataStore: DataStore<Preferences>
) {
    suspend fun storeIntoRoom(dog: Dog) {
        dogDao.insertDog(dog)
    }

    suspend fun storeRegistration() {
        dataStore.edit {
            it[KEY_IS_REGISTERED] = true
        }
    }
}