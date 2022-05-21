package com.jangjh123.shallwegoforawalk.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jangjh123.shallwegoforawalk.data.local.DogDao
import com.jangjh123.shallwegoforawalk.data.repository.MainRepository
import com.jangjh123.shallwegoforawalk.data.repository.RegisterRepository
import com.jangjh123.shallwegoforawalk.data.repository.SplashRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideSplashRepository(
        dataStore: DataStore<Preferences>
    ) = SplashRepository(dataStore)

    @ViewModelScoped
    @Provides
    fun provideRegisterRepository(dogDao: DogDao) = RegisterRepository(dogDao)

    @ViewModelScoped
    @Provides
    fun provideMainRepository(dogDao: DogDao) = MainRepository(dogDao)
}
