package com.jangjh123.shallwegoforawalk.ui.activity.home

import androidx.lifecycle.ViewModel
import com.jangjh123.shallwegoforawalk.data.model.AddressStateHandler
import com.jangjh123.shallwegoforawalk.data.model.DogsStateHandler
import com.jangjh123.shallwegoforawalk.data.model.WeatherStateHandler
import com.jangjh123.shallwegoforawalk.data.model.weather.AddressVO
import com.jangjh123.shallwegoforawalk.data.model.weather.WeatherVO
import com.jangjh123.shallwegoforawalk.data.repository.HomeRepository
import com.jangjh123.shallwegoforawalk.util.CoroutineScopes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {
    private val _weatherVOFlow = MutableStateFlow<WeatherStateHandler?>(null)
    val weatherVOFlow: StateFlow<WeatherStateHandler?>
        get() = _weatherVOFlow

    private val _dogsFlow = MutableStateFlow<DogsStateHandler?>(null)
    val dogsFlow: StateFlow<DogsStateHandler?>
        get() = _dogsFlow

    private val _addressFlow = MutableStateFlow<AddressStateHandler?>(null)
    val addressFlow: StateFlow<AddressStateHandler?>
        get() = _addressFlow

    init {
        CoroutineScopes.io {
            repository.fetchDogs().collect { dogs ->
                if (dogs.isNotEmpty()) {
                    _dogsFlow.emit(
                        DogsStateHandler.Success(
                            dogs
                        )
                    )
                } else {
                    _dogsFlow.emit(DogsStateHandler.Failure("List is empty."))
                }
            }
        }
    }

    fun getWeatherVO(latitude: Double, longitude: Double) {
        CoroutineScopes.io {
            repository.fetchWeather("$latitude,$longitude").collect { data ->
                when (data) {
                    is WeatherVO -> {
                        _weatherVOFlow.emit(WeatherStateHandler.Success(data))
                        repository.storeCoordinate(latitude, longitude)
                    }
                    is String -> {
                        _weatherVOFlow.emit(WeatherStateHandler.Failure(data))
                    }
                }
            }
        }
    }

    fun getAddress(latitude: Double, longitude: Double) {
        CoroutineScopes.io {
            repository.fetchAddress(latitude, longitude).collect { data ->
                when (data) {
                    is AddressVO -> {
                        _addressFlow.emit(AddressStateHandler.Success(data))
                    }
                    is String -> {
                        _addressFlow.emit(AddressStateHandler.Failure(data))
                    }
                }
            }
        }
    }

    fun getStoredCoordinate() = repository.loadStoredCoordinate()
}