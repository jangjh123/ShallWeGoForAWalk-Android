package com.jangjh123.shallwegoforawalk.ui.activity.home

import androidx.lifecycle.ViewModel
import com.jangjh123.shallwegoforawalk.data.model.weather.WeatherUiState
import com.jangjh123.shallwegoforawalk.data.model.weather.WeatherVO
import com.jangjh123.shallwegoforawalk.data.repository.HomeRepository
import com.jangjh123.shallwegoforawalk.util.CoroutineScopes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {
    private val _weatherVOFlow = MutableStateFlow<WeatherUiState?>(null)
    val weatherVOFlow: StateFlow<WeatherUiState?>
        get() = _weatherVOFlow

    fun getWeatherVO(latitude: Double, longitude: Double) {
        CoroutineScopes.io {
            repository.fetchWeather("$latitude,$longitude")
                .collect { data ->
                    when (data) {
                        is WeatherVO -> {
                            _weatherVOFlow.tryEmit(WeatherUiState.Success(data))
                        }
                        is String -> {
                            _weatherVOFlow.tryEmit(WeatherUiState.Failure(data))
                        }
                    }
                }
        }
    }

    fun getDogs() {
        repository.fetchDogs()
    }
}