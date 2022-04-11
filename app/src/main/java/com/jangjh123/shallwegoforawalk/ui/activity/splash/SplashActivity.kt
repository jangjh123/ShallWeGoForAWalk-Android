package com.jangjh123.shallwegoforawalk.ui.activity.splash

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.databinding.ActivitySplashBinding
import com.jangjh123.shallwegoforawalk.ui.activity.register.RegisterActivity
import com.jangjh123.shallwegoforawalk.ui.base.BaseActivity
import com.jangjh123.shallwegoforawalk.ui.component.NoticeDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val viewModel: SplashViewModel by viewModels()

    override fun startProcess() {
        lifecycleScope.launch {
            delay(1500L)
            when (isConnectedToNetwork()) {
                true -> {
                    viewModel.getRegistration(onComplete = {
                        when (it) {
                            true -> {
                                // TODO: 저장한 강아지 있을 때
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
                    })
                }
                false -> {
                    NoticeDialog(
                        title = getString(R.string.dialog_network_title),
                        body = getString(R.string.dialog_network_body),
                        buttonText = getString(R.string.quit),
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

    private fun isConnectedToNetwork(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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
}