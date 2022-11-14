package com.jangjh123.shallwegoforawalk.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.jangjh123.data_store.KEY_IS_REGISTERED
import com.jangjh123.shallwegoforawalk.data.local.DogDao
import com.jangjh123.shallwegoforawalk.util.CoroutineScopes
import javax.inject.Inject

class DogListRepository @Inject constructor(
    private val dogDao: DogDao,
    private val dataStore: DataStore<Preferences>
) {
    fun removeDogById(id: Int, onComplete: () -> Unit) {
        CoroutineScopes.io {
            dogDao.deleteDog(id)
        }.invokeOnCompletion {
            onComplete()
        }
    }

    suspend fun setRegistered() {
        dataStore.edit {
            it[KEY_IS_REGISTERED] = false
        }
    }

    fun getNewDogList() = dogDao.getAllDog()
}