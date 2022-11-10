package com.jangjh123.shallwegoforawalk.ui.activity.home

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.data.model.DogsStateHandler
import com.jangjh123.shallwegoforawalk.data.model.WeatherStateHandler
import com.jangjh123.shallwegoforawalk.data.model.weather.Dog
import com.jangjh123.shallwegoforawalk.data.model.weather.WeatherVO
import com.jangjh123.shallwegoforawalk.databinding.ActivityHomeBinding
import com.jangjh123.shallwegoforawalk.ui.activity.register.RegisterActivity
import com.jangjh123.shallwegoforawalk.ui.base.BaseActivity
import com.jangjh123.shallwegoforawalk.ui.component.CautionDialog
import com.jangjh123.shallwegoforawalk.ui.component.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.combineTransform

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

    private fun getLocation() {
        LocationServices.getFusedLocationProviderClient(this)
            .getCurrentLocation(
                LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY,
                object : CancellationToken() {
                    override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken {
                        return CancellationTokenSource().token
                    }

                    override fun isCancellationRequested(): Boolean {
                        return false
                    }
                }).addOnSuccessListener {
                viewModel.getWeatherVO(it.latitude, it.longitude)

            }.addOnFailureListener {
                // todo : save last location with dataStore. if doesn't exist, use seoul's one.
            }
    }

    private fun initViewPager(weather: WeatherVO, dogs: List<Dog>) {
        binding.viewPager.adapter =
            ViewPagerAdapter(weather, dogs, supportFragmentManager, lifecycle)
    }

    fun showAddDogScreen(view: View) {
    }

    fun showMainScreen() {
    }

    fun showCaution(view: View) {
        CautionDialog().show(supportFragmentManager, "caution")
    }

    fun addNewDog() {
        startActivity(
            Intent(this@HomeActivity, RegisterActivity::class.java)
        )
    }

    override fun setObserver() {
        lifecycleScope.launchWhenCreated {
            viewModel.weatherVOFlow.combineTransform(viewModel.dogsFlow) { weatherState, dogsState ->
                emit(Pair(weatherState, dogsState))
            }.collect { combined ->
                when {
                    combined.first == null || combined.second == null -> {
                        showProgress()
                    }
                    combined.first is WeatherStateHandler.Success && combined.second is DogsStateHandler.Success -> {
                        initViewPager(
                            (combined.first as WeatherStateHandler.Success).data,
                            (combined.second as DogsStateHandler.Success).data
                        )
                        cancel("success")
                    }
                    combined.first is WeatherStateHandler.Failure -> {
                        cancel("weather")
                    }
                    combined.second is DogsStateHandler.Failure -> {
                        cancel("dogs")
                    }
                }
            }
        }.invokeOnCompletion {
            dismissProgress()
        }
    }
}
