package com.jangjh123.shallwegoforawalk.ui.fragment.dog

import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.data.model.weather.Dog
import com.jangjh123.shallwegoforawalk.data.model.weather.WeatherVO
import com.jangjh123.shallwegoforawalk.databinding.FragmentMainBinding
import com.jangjh123.shallwegoforawalk.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogFragment(private val weather: WeatherVO, dog: Dog) : BaseFragment<FragmentMainBinding>(R.layout.fragment_dog) {

}