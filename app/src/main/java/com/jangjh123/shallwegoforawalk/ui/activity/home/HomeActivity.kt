package com.jangjh123.shallwegoforawalk.ui.activity.home

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.data.model.AddressStateHandler
import com.jangjh123.shallwegoforawalk.data.model.DogsStateHandler
import com.jangjh123.shallwegoforawalk.data.model.WeatherStateHandler
import com.jangjh123.shallwegoforawalk.data.model.weather.Dog
import com.jangjh123.shallwegoforawalk.data.model.weather.WeatherVO
import com.jangjh123.shallwegoforawalk.databinding.ActivityHomeBinding
import com.jangjh123.shallwegoforawalk.ui.activity.dog_list.DogListActivity
import com.jangjh123.shallwegoforawalk.ui.base.BaseActivity
import com.jangjh123.shallwegoforawalk.ui.component.CautionDialog
import com.jangjh123.shallwegoforawalk.ui.component.ConfirmDialog
import com.jangjh123.shallwegoforawalk.ui.component.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.combineTransform
import kotlin.system.exitProcess

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {
    private val viewModel: HomeViewModel by viewModels()

    override fun initViewDataBinding() {
        super.initViewDataBinding()
        binding.activity = this@HomeActivity
    }

    override fun startProcess() {
        getLocation()
        setObserver()
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        LocationServices.getFusedLocationProviderClient(this).getCurrentLocation(
            102,
            object : CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken {
                    return CancellationTokenSource().token
                }

                override fun isCancellationRequested(): Boolean {
                    return false
                }
            }).addOnSuccessListener {
            viewModel.getWeatherVO(it.latitude, it.longitude)
            viewModel.getAddress(it.latitude, it.longitude)

        }.addOnFailureListener {
            // todo : save last location with dataStore. if doesn't exist, use seoul's one.
        }
    }

    override fun setObserver() {
        lifecycleScope.launchWhenCreated {
            combineTransform(
                viewModel.weatherVOFlow,
                viewModel.dogsFlow,
                viewModel.addressFlow
            ) { weatherState, dogsState, addressState ->
                emit(Triple(weatherState, dogsState, addressState))
            }.collect { combined ->
                when {
                    combined.first == null || combined.second == null || combined.third == null -> {
                        showProgress()
                    }
                    combined.first is WeatherStateHandler.Success
                            && combined.second is DogsStateHandler.Success
                            && combined.third is AddressStateHandler.Success -> {
                        val weather = (combined.first as WeatherStateHandler.Success).data
                        val dogs = (combined.second as DogsStateHandler.Success).data
                        val address = (combined.third as AddressStateHandler.Success).data
                        setWeatherData(weather)
                        initViewPager(weather, dogs, address.name)
                        cancel("success")
                    }
                    combined.first is WeatherStateHandler.Failure -> {
                        cancel("weather")
                    }
                    combined.second is DogsStateHandler.Failure -> {
                        cancel("dogs")
                    }
                    combined.third is AddressStateHandler.Failure -> {
                        cancel("address")
                    }
                }
            }
        }.invokeOnCompletion {
            dismissProgress()
        }
    }

    private fun initViewPager(weather: WeatherVO, dogs: List<Dog>, address: String) {
        binding.viewPager.adapter =
            ViewPagerAdapter(dogs.size, weather, dogs, address, supportFragmentManager, lifecycle)
        binding.indicator.setViewPager(binding.viewPager)
    }

    private fun setWeatherData(weather: WeatherVO) {
        with(binding) {
            BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
            curTemp = weather.hourlyList[0].temp.toString()
            maxTemp = weather.maxTemp.toString()
            minTemp = weather.minTemp.toString()
            fine = weather.fine.toString()
            uFine = weather.uFine.toString()
            pop = weather.hourlyList[0].pop.toString()
            humidity = weather.hourlyList[0].humidity.toString()
            imageUrl = weather.hourlyList[0].icon

            fineText = when {
                weather.fine > 355 -> {
                    "매우 나쁨"
                }
                weather.fine > 255 -> {
                    "나쁨"
                }
                weather.fine > 155 -> {
                    "약간 나쁨"
                }
                weather.fine > 55 -> {
                    "보통"
                }
                else -> {
                    "좋음"
                }
            }

            uFineText = when {
                weather.uFine > 151 -> {
                    "매우 나쁨"
                }
                weather.uFine > 56 -> {
                    "나쁨"
                }
                weather.uFine > 36 -> {
                    "약간 나쁨"
                }
                weather.uFine > 12 -> {
                    "보통"
                }
                else -> {
                    "좋음"
                }
            }
        }
    }

    fun addDog(view: View) {
        startActivity(Intent(this@HomeActivity, DogListActivity::class.java).apply {
            putExtra(
                "originalSize",
                (viewModel.dogsFlow.value as DogsStateHandler.Success).data.size
            )
        })
    }

    fun showCaution(view: View) {
        CautionDialog().show(supportFragmentManager, "caution")
    }

    override fun onBackPressed() {
        ConfirmDialog(
            title = getString(R.string.dialog_app_quit_title),
            body = getString(R.string.dialog_app_quit_body),
            cancelButtonText = getString(R.string.dialog_cancel),
            confirmButtonText = getString(R.string.dialog_quit),
            onClickCancel = {

            },
            onClickConfirm = {
                moveTaskToBack(true)
                finishAndRemoveTask()
                exitProcess(0)
            }
        ).show(supportFragmentManager, "dialog_quit")
    }
}
