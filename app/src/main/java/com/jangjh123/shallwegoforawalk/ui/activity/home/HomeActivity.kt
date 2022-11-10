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
import com.jangjh123.shallwegoforawalk.databinding.ActivityHomeBinding
import com.jangjh123.shallwegoforawalk.ui.activity.register.RegisterActivity
import com.jangjh123.shallwegoforawalk.ui.base.BaseActivity
import com.jangjh123.shallwegoforawalk.ui.component.CautionDialog
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

    private fun initViewPager() {
//        binding.viewPager.adapter = ViewPagerAdapter()
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
            }.collect { data ->
                when {
                    data.first == null || data.second == null -> {
                        showProgress()
                    }
                    data.first is WeatherStateHandler.Success && data.second is DogsStateHandler.Success -> {
                        // todo send through fragment's parameter
                        cancel("success")
                    }
                    data.first is WeatherStateHandler.Failure -> {
                        cancel("weather")
                    }
                    data.second is DogsStateHandler.Failure -> {
                        cancel("dogs")
                    }
                }
            }
        }.invokeOnCompletion {
            dismissProgress()
        }
    }
}
