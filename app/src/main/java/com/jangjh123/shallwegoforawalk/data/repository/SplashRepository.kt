package com.jangjh123.shallwegoforawalk.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jangjh123.data_store.KEY_IS_REGISTERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SplashRepository @Inject constructor(
    dataStore: DataStore<Preferences>
) {
    private val isRegisteredFlow: Flow<Boolean> = dataStore.data.map {
        it[KEY_IS_REGISTERED] ?: false
    }

    fun readRegistration() = isRegisteredFlow
}