package com.jangjh123.shallwegoforawalk.ui.fragment.dog

import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.data.model.weather.Dog
import com.jangjh123.shallwegoforawalk.data.model.weather.WeatherVO
import com.jangjh123.shallwegoforawalk.databinding.FragmentDogBinding
import com.jangjh123.shallwegoforawalk.ui.base.BaseFragment
import com.jangjh123.shallwegoforawalk.util.WalkInfoProvider

class DogFragment(
    private val weather: WeatherVO,
    private val dog: Dog,
    private val addressName: String
) :
    BaseFragment<FragmentDogBinding>(R.layout.fragment_dog) {
    private val reasonList = ArrayList<String>()

    override fun initViewDataBinding() {

    }

    override fun startProcess() {
        initView()
    }

    private fun initView() {
        with(binding) {
            val walkInfoProvider = WalkInfoProvider(requireContext())
            val mainPoint = walkInfoProvider.editPoint(weather, 0, reasonList)
            curPoint = mainPoint.toString()
            name = dog.name
            backgroundColor = walkInfoProvider.getColorByPoint(mainPoint)
            image = walkInfoProvider.getImageByPointAndSize(mainPoint, dog.size)
            mainText = walkInfoProvider.getMainTextByPoint(mainPoint)
            hourlyPoint = List(6) { walkInfoProvider.editPoint(weather, it, null).toString() }
            times = walkInfoProvider.getTimeTable()
            address = addressName
        }
    }
}