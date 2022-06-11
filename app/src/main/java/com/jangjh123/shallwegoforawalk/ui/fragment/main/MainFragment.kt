package com.jangjh123.shallwegoforawalk.ui.fragment.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.PagerSnapHelper
import com.bumptech.glide.Glide
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.data.model.weather.WeatherVO
import com.jangjh123.shallwegoforawalk.databinding.FragmentMainBinding
import com.jangjh123.shallwegoforawalk.ui.base.BaseFragment
import com.jangjh123.shallwegoforawalk.ui.component.ConfirmDialog
import com.jangjh123.shallwegoforawalk.ui.component.MainAdapter
import com.jangjh123.shallwegoforawalk.ui.component.NoticeDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var mainAdapter: MainAdapter
    private lateinit var address: String
    private var locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            viewModel.getWeatherData(
                p0.locations[0].latitude,
                p0.locations[0].longitude,
                onNetworkError = {
                    NoticeDialog(
                        title = getString(R.string.dialog_network_title),
                        body = getString(R.string.dialog_network_body),
                        buttonText = getString(R.string.dialog_quit),
                        onClickButton = {
                            requireActivity().moveTaskToBack(true)
                            requireActivity().finishAndRemoveTask()
                            exitProcess(0)
                        }
                    ).show(childFragmentManager, "dialog_network_error")
                })

            address = Geocoder(requireContext()).getFromLocation(
                p0.locations[0].latitude,
                p0.locations[0].longitude,
                1
            )[0].getAddressLine(0).toString().removeRange(0, 5) + "(현재위치)"
        }
    }

    private val locationRequester =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value
            }
            if (granted) {
                getCurrentLocation()
            } else {
                hideProgress()
                NoticeDialog(
                    getString(R.string.fragment_main_need_permission_title),
                    getString(R.string.fragment_main_need_permission_body),
                    getString(R.string.dialog_quit)
                ) {
                    requireActivity().finish()
                }.show(childFragmentManager, "dialog_need_permission")
            }
        }

    override fun setObserver() {
        viewModel.weatherData.observe(viewLifecycleOwner) {
            showData(it)
        }
    }

    override fun startProcess() {
        showProgress()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            BottomSheetBehavior.from(binding.bottomSheet).state =
                BottomSheetBehavior.STATE_EXPANDED
            locationRequester.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else {
            getCurrentLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val fusedLocationManager = LocationServices.getFusedLocationProviderClient(requireContext())

        try {
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
            getCurrentLocation()
        }
    }

    private fun showData(weatherVO: WeatherVO) {
        with(binding) {
            BottomSheetBehavior.from(binding.bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
            textviewTempCur.text = weatherVO.hourlyList[0].temp.toString()
            textviewTempHigh.text = weatherVO.maxTemp.toString()
            textviewTempLow.text = weatherVO.minTemp.toString()

            textviewHumidity.text = weatherVO.hourlyList[0].humidity.toString()
            textviewRainPossibility.text = weatherVO.hourlyList[0].pop.toString()
            textviewUltraFineDustValue.text = weatherVO.uFine.toString()
            textviewFineDustValue.text = weatherVO.fine.toString()

            Glide.with(requireContext())
                .load(weatherVO.hourlyList[0].icon)
                .into(imageviewWeatherIcon)

            val ultraFineDust = weatherVO.uFine
            textviewUltraFineDustText.text = when {
                ultraFineDust > 151 -> {
                    "매우 나쁨"
                }
                ultraFineDust > 56 -> {
                    "나쁨"
                }
                ultraFineDust > 36 -> {
                    "약간 나쁨"
                }
                ultraFineDust > 12 -> {
                    "보통"
                }
                else -> {
                    "좋음"
                }
            }

            val fineDust = weatherVO.fine
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

            mainAdapter = MainAdapter(
                weatherVO,
                address,
                onClickQuestionMark = { reasons ->

                    val sb = StringBuilder()
                    for (i in reasons.indices) {
                        sb.append("${reasons[i]}\n")
                    }

                    NoticeDialog(
                        getString(R.string.fragment_main_reason),
                        when {
                            reasons.isNotEmpty() -> {
                                sb.toString()
                            }
                            else -> {
                                getString(R.string.fragment_main_no_reason)
                            }
                        },
                        getString(R.string.dialog_confirm)
                    ) { }.show(childFragmentManager, "dialog_reason")
                })

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
        hideProgress()
    }
}