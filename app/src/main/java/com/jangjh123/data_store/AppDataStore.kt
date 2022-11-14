package com.jangjh123.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("shallWeGoForAWalk")
val KEY_IS_REGISTERED = booleanPreferencesKey("isRegistered")
val KEY_ADDRESS_LATITUDE = doublePreferencesKey("latitude")
val KEY_ADDRESS_LONGITUDE = doublePreferencesKey("longitude")