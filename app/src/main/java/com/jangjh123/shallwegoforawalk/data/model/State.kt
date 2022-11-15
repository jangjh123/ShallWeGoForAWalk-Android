package com.jangjh123.shallwegoforawalk.data.model

import com.jangjh123.shallwegoforawalk.data.model.weather.AddressVO
import com.jangjh123.shallwegoforawalk.data.model.weather.Dog
import com.jangjh123.shallwegoforawalk.data.model.weather.WeatherVO

sealed class WeatherStateHandler {
    data class Success(val data: WeatherVO) : WeatherStateHandler()
    data class Failure(val errorMessage: String) : WeatherStateHandler()
}

sealed class DogsStateHandler {
    data class Success(val data: List<Dog>) : DogsStateHandler()
    data class Failure(val errorMessage: String) : DogsStateHandler()
}

sealed class AddressStateHandler {
    data class Success(val data: AddressVO) : AddressStateHandler()
    data class Failure(val errorMessage: String) : AddressStateHandler()
}