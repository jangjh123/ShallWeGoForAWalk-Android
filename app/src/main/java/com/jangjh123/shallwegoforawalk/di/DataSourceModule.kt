package com.jangjh123.shallwegoforawalk.di

import com.jangjh123.shallwegoforawalk.data.remote.DataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideDataSource() = DataSource()
}