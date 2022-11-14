package com.jangjh123.shallwegoforawalk.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jangjh123.data_store.KEY_IS_REGISTERED
import com.jangjh123.shallwegoforawalk.data.local.DogDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SplashRepository @Inject constructor(
    private val dogDao: DogDao,
    dataStore: DataStore<Preferences>
) {

    private val registeredFlow = dataStore.data.map {
        it[KEY_IS_REGISTERED] ?: false
    }.flowOn(Dispatchers.IO)

    fun isRegistered() = registeredFlow
}