package com.jangjh123.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "dog")
val KEY_IS_REGISTERED = booleanPreferencesKey("is_registered")
