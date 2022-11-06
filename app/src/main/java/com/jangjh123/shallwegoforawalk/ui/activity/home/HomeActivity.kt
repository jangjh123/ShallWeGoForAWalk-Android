package com.jangjh123.shallwegoforawalk.ui.activity.home

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.view.View
import androidx.activity.viewModels
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.databinding.ActivityHomeBinding
import com.jangjh123.shallwegoforawalk.ui.activity.register.RegisterActivity
import com.jangjh123.shallwegoforawalk.ui.base.BaseActivity
import com.jangjh123.shallwegoforawalk.ui.component.CautionDialog
import com.jangjh123.shallwegoforawalk.ui.component.ConfirmDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {
    private val viewModel: HomeViewModel by viewModels()

    override fun initViewDataBinding() {
        super.initViewDataBinding()
        binding.activity = this@HomeActivity
    }

    override fun startProcess() {
        getLocation()
        initViewPager()
    }

    private var locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            println("Latitude ${p0.lastLocation.latitude} Longitude ${p0.lastLocation.longitude}")
            // todo : permission request
        }
    }

    private fun getLocation() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val fusedLocationManager =
            LocationServices.getFusedLocationProviderClient(this@HomeActivity)

        try {
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                requestLocationService(locationManager)
            } else {
                fusedLocationManager.requestLocationUpdates(
                    LocationRequest.create().apply {
                        interval = 100
                        fastestInterval = 50
                        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                        numUpdates = 1
                        maxWaitTime = 100
                    },
                    locationCallback,
                    Looper.getMainLooper()
                )
            }
        } catch (e: Exception) {
            println("ERROR $e")
        }
    }

    private fun requestLocationService(locationManager: LocationManager) {
        ConfirmDialog(
            title = getString(R.string.dialog_location_service_title),
            body = getString(R.string.dialog_location_service_body),
            cancelButtonText = getString(R.string.dialog_location_service_cancel),
            confirmButtonText = getString(R.string.dialog_location_service_confirm),
            onClickCancel = {
                finish()
            },
            onClickConfirm = {
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
        ).show(supportFragmentManager, "dialog_location_service")
    }

    private fun initViewPager() {

//        binding.viewPager.adapter = ViewPagerAdapter() // 강아지 정보와 날씨를 가져와 파라미터로 넘겨 줌
    }

    fun showAddDogScreen(view: View) {
//        navController.navigate(R.id.action_main_to_dog_list)
//        binding.imageviewHomeAddDog.visibility = View.GONE
//        binding.imageviewHomeCaution.visibility = View.GONE
    }

    fun showMainScreen() {
//        navController.navigate(R.id.action_dog_list_to_main)
//        binding.imageviewHomeAddDog.visibility = View.VISIBLE
//        binding.imageviewHomeCaution.visibility = View.VISIBLE
    }

    fun showCaution(view: View) {
        CautionDialog().show(supportFragmentManager, "caution")
    }

    fun addNewDog() {
        startActivity(
            Intent(this@HomeActivity, RegisterActivity::class.java)
        )
    }
}