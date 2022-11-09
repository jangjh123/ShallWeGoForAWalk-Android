package com.jangjh123.shallwegoforawalk.ui.fragment.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jangjh123.shallwegoforawalk.data.model.DogListTypes.Dog
import com.jangjh123.shallwegoforawalk.data.model.weather.WeatherVO
import com.jangjh123.shallwegoforawalk.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _dogList = MutableLiveData<List<Dog>>()
    val dogList: LiveData<List<Dog>>
        get() = _dogList

    private val _weatherData = MutableLiveData<WeatherVO>()
    val weatherData: LiveData<WeatherVO>
        get() = _weatherData

    fun loadDogList() {
//        CoroutineScope(dispatcher).launch {
//            _dogList.postValue(repository.getDogList())
    }
}