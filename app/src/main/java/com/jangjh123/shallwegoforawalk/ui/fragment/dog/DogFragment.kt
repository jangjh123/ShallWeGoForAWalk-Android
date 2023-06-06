package com.jangjh123.shallwegoforawalk.ui.fragment.dog

import android.view.View
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.data.model.weather.Dog
import com.jangjh123.shallwegoforawalk.data.model.weather.WeatherVO
import com.jangjh123.shallwegoforawalk.databinding.FragmentDogBinding
import com.jangjh123.shallwegoforawalk.ui.base.BaseFragment
import com.jangjh123.shallwegoforawalk.ui.component.NoticeDialog
import com.jangjh123.shallwegoforawalk.util.WalkInfoProvider

class DogFragment(
    private val weather: WeatherVO,
    private val dog: Dog,
    private val addressName: String
) :
    BaseFragment<FragmentDogBinding>(R.layout.fragment_dog) {
    private val reasonList = ArrayList<String>()

    override fun initViewDataBinding() {
        binding.fragment = this@DogFragment
    }

    override fun startProcess() {
        initView()
    }

    private fun initView() {
        with(binding) {
            val walkInfoProvider = WalkInfoProvider(requireContext())
            val mainPoint = walkInfoProvider.editPoint(dog, weather, 0, reasonList)
            curPoint = mainPoint.toString()
            name = dog.name
            backgroundDrawable = walkInfoProvider.getBackgroundByPoint(mainPoint)
            image = walkInfoProvider.getImageByPointAndSize(mainPoint, dog.size)
            mainText = walkInfoProvider.getMainTextByPoint(mainPoint)
            hourlyPoint = List(6) { walkInfoProvider.editPoint(dog, weather, it, null).toString() }
            times = walkInfoProvider.getTimeTable()
            address = addressName
        }
    }

    fun onClickReason(view: View) {
        val sb = StringBuilder().also {
            for (i in reasonList.indices) {
                it.append("${reasonList[i]}\n")
            }
        }

        NoticeDialog(
            getString(R.string.fragment_main_reason),
            when {
                reasonList.isNotEmpty() -> {
                    sb.toString()
                }
                else -> {
                    getString(R.string.fragment_main_no_reason)
                }
            },
            getString(R.string.dialog_confirm)
        ) { }.show(childFragmentManager, "dialog_reason")
    }
}