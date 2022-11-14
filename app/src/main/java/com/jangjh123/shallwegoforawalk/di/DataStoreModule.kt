package com.jangjh123.shallwegoforawalk.di

import android.content.Context
import com.jangjh123.data_store.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    @Singleton
    @Provides
    fun provideAppDataStore(@ApplicationContext context: Context) = context.dataStore
}