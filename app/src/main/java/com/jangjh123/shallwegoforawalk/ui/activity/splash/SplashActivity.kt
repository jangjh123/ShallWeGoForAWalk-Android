package com.jangjh123.shallwegoforawalk.ui.activity.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.databinding.ActivitySplashBinding
import com.jangjh123.shallwegoforawalk.ui.activity.home.HomeActivity
import com.jangjh123.shallwegoforawalk.ui.activity.register.RegisterActivity
import com.jangjh123.shallwegoforawalk.ui.base.BaseActivity
import com.jangjh123.shallwegoforawalk.ui.component.ConfirmDialog
import com.jangjh123.shallwegoforawalk.ui.component.NoticeDialog
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.exitProcess

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val viewModel: SplashViewModel by viewModels()
    private val locationService by lazy { getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    private val permissionCallback =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                showLocationServiceDialog()
            } else {
                finishApp()
            }
        }

    private val serviceCallback =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (locationService.isProviderEnabled("gps")) {
                changeActivity()
            } else {
                finishApp()
            }
        }

    override fun startProcess() {
        lifecycleScope.launch {
            delay(1500L)
            when (isConnectedToNetwork()) {
                true -> {
                    checkAuthority(
                        onBothGranted = {
                            changeActivity()
                        },
                        onElse = {
                            requestPermission()
                        })
                }
                else -> {
                    NoticeDialog(
                        title = getString(R.string.dialog_network_title),
                        body = getString(R.string.dialog_network_body),
                        buttonText = getString(R.string.dialog_quit),
                        onClickButton = {
                            moveTaskToBack(true)
                            finishAndRemoveTask()
                            exitProcess(0)
                        }
                    ).show(supportFragmentManager, "dialog_network_error")
                }
            }
        }
    }

    private fun changeActivity() {
        viewModel.getRegistration(
            onComplete = { isDogRegistered ->
                when (isDogRegistered) {
                    true -> {
                        startActivity(
                            Intent(
                                this@SplashActivity,
                                HomeActivity::class.java
                            )
                        )
                    }
                    false -> {
                        startActivity(
                            Intent(
                                this@SplashActivity,
                                RegisterActivity::class.java
                            )
                        )
                    }
                }
                finish()
            })
    }

    private inline fun checkAuthority(
        crossinline onBothGranted: () -> Unit,
        crossinline onElse: () -> Unit
    ) {
        val isPermissionGranted = ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val isServiceGranted =
            (getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(
                LocationManager.GPS_PROVIDER
            )

        if (isPermissionGranted && isServiceGranted) {
            onBothGranted()
        } else {
            onElse()
        }
    }

    private fun requestPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionCallback.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        } else {
            requestLocationService()
        }
    }

    private fun requestLocationService() {
        if (!locationService.isProviderEnabled("gps")) {
            showLocationServiceDialog()
        } else {
            changeActivity()
        }
    }

    private fun showLocationServiceDialog() {
        ConfirmDialog(
            title = getString(R.string.dialog_location_service_title),
            body = getString(R.string.dialog_location_service_body),
            cancelButtonText = getString(R.string.dialog_location_service_cancel),
            confirmButtonText = getString(R.string.dialog_location_service_confirm),
            onClickCancel = {
                finish()
            },
            onClickConfirm = {
                serviceCallback.launch(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
        ).show(supportFragmentManager, "location_service")
    }

    private fun isConnectedToNetwork(): Boolean {
        val connectivityManager =
            getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val active = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            active.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            active.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            active.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            active.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }

    private fun finishApp() {
        moveTaskToBack(true)
        finishAndRemoveTask()
        exitProcess(0)
    }
}