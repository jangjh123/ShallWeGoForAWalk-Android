package com.jangjh123.shallwegoforawalk.ui.fragment.main

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.PagerSnapHelper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.databinding.FragmentMainBinding
import com.jangjh123.shallwegoforawalk.ui.base.BaseFragment
import com.jangjh123.shallwegoforawalk.ui.component.MainAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private val viewModel: MainViewModel by viewModels()
    private val mainAdapter = MainAdapter()

    override fun initViewDataBinding() {
        binding.adapter = mainAdapter
    }

    override fun startProcess() {
        BottomSheetBehavior.from(binding.bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
        showData()
        viewModel.getWeatherData(35.85f, 128.60f)
        initView()
        showWeatherData()
    }

    private fun showData() {
        viewModel.dogList.observe(viewLifecycleOwner) {
            mainAdapter.submitList(it)
        }
    }

    private fun initView() {
        with(binding) {
            PagerSnapHelper().run {
                this.attachToRecyclerView(recyclerviewMain)
                indicator.attachToRecyclerView(recyclerviewMain, this)
            }
            mainAdapter.registerAdapterDataObserver(indicator.adapterDataObserver)
        }
    }

    private fun showWeatherData() {
        viewModel.weatherData.observe(viewLifecycleOwner) { data ->
            with(binding) {
                textviewTempCur.text = data.hourlyWeatherList[0].temp.toString()
                textviewTempHigh.text = data.maxTemp.toString()
                textviewTempLow.text = data.minTemp.toString()

                textviewHumidity.text = data.hourlyWeatherList[0].humidity.toString()
                textviewRainPossiblity.text = data.hourlyWeatherList[0].pop.toString()
                textviewUltraFineDustValue.text = data.ultraFineDust.toString()
                textviewFineDustValue.text = data.fineDust.toString()

                imageviewWeatherIcon.setImageResource(
                    when (data.hourlyWeatherList[0].icon) {
                        "01d", "01n" -> {
                            R.drawable.icon_wt_sunny
                        }
                        "02d", "02n" -> {
                            R.drawable.icon_wt_sunny_cloudy
                        }
                        "03d", "03n", "04d", "04n" -> {
                            R.drawable.icon_wt_03_cloudu
                        }
                        "09d", "09n" -> {
                            R.drawable.icon_wt_07_shower
                        }
                        "10d", "10n", "11d", "11n" -> {
                            R.drawable.icon_wt_04_rainy
                        }
                        "13d", "13n" -> {
                            R.drawable.icon_wt_06_snowy
                        }
                        else -> {
                            R.drawable.icon_wt_03_cloudu
                        }
                    }
                )

                val ultraFineDust = data.ultraFineDust
                textviewUltraFineDustText.text = when {
                    ultraFineDust > 151 -> {
                        "매우 나쁨"
                    }
                    ultraFineDust > 56 -> {
                        "나쁨"
                    }
                    ultraFineDust > 36 -> {
                        "보통"
                    }
                    ultraFineDust > 12 -> {
                        "보통"
                    }
                    else -> {
                        "좋음"
                    }
                }

                val fineDust = data.fineDust
                textviewFineDustText.text = when {
                    fineDust > 355 -> {
                        "매우 나쁨"
                    }
                    fineDust > 255 -> {
                        "나쁨"
                    }
                    fineDust > 155 -> {
                        "약간 나쁨"
                    }
                    fineDust > 55 -> {
                        "보통"
                    }
                    else -> {
                        "좋음"
                    }
                }
            }
        }
    }
}