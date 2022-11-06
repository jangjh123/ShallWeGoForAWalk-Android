package com.jangjh123.shallwegoforawalk.di

import android.content.Context
import androidx.room.Room
import com.jangjh123.shallwegoforawalk.data.local.DogDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDogDatabase(@ApplicationContext context: Context) = Room
        .databaseBuilder(
            context,
            DogDatabase::class.java,
            DogDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
        .build()


    @Singleton
    @Provides
    fun provideDogDao(dogDatabase: DogDatabase) = dogDatabase.getDogDao()
}