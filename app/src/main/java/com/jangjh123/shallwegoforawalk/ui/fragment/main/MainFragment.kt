package com.jangjh123.shallwegoforawalk.ui.fragment.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.PagerSnapHelper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.databinding.FragmentMainBinding
import com.jangjh123.shallwegoforawalk.ui.base.BaseFragment
import com.jangjh123.shallwegoforawalk.ui.component.ConfirmDialog
import com.jangjh123.shallwegoforawalk.ui.component.MainAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var mainAdapter: MainAdapter
    private lateinit var locationCallback: LocationCallback

    override fun startProcess() {
        BottomSheetBehavior.from(binding.bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED

        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val fusedLocationManager = LocationServices.getFusedLocationProviderClient(requireContext())

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            ActivityCompat.requestPermissions(requireActivity(), permissions, 1)

            fusedLocationManager.requestLocationUpdates(
                LocationRequest(),
                locationCallback,
                Looper.getMainLooper()
            )

        } else {
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                ConfirmDialog(
                    title = getString(R.string.dialog_location_service_title),
                    body = getString(R.string.dialog_location_service_body),
                    cancelButtonText = getString(R.string.dialog_location_service_cancel),
                    confirmButtonText = getString(R.string.dialog_location_service_confirm),
                    onClickCancel = {
                        requireActivity().finish()
                    },
                    onClickConfirm = {
                        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                ).show(childFragmentManager, "dialog_location_service")
            } else {

                locationCallback = object : LocationCallback() {
                    override fun onLocationResult(p0: LocationResult) {
                        viewModel.getWeatherData(p0.locations[0].latitude, p0.locations[0].longitude)
                    }
                }

                fusedLocationManager.requestLocationUpdates(
                    LocationRequest(),
                    locationCallback,
                    Looper.getMainLooper()
                )
            }
        }

        showData()
    }

    private fun showData() {
        viewModel.weatherData.observe(viewLifecycleOwner) { data ->
            Log.d("TEST", data.toString())
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

                mainAdapter = MainAdapter(data, "TEST")
                recyclerviewMain.adapter = mainAdapter
                viewModel.dogList.observe(viewLifecycleOwner) {
                    mainAdapter.submitList(it)
                }

                PagerSnapHelper().run {
                    if (recyclerviewMain.onFlingListener != null) {
                        recyclerviewMain.onFlingListener = null
                    }
                    this.attachToRecyclerView(recyclerviewMain)
                    indicator.attachToRecyclerView(recyclerviewMain, this)
                }
                mainAdapter.registerAdapterDataObserver(indicator.adapterDataObserver)
            }
        }
    }
}