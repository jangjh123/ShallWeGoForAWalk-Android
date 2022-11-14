package com.jangjh123.shallwegoforawalk.ui.activity.splash

import androidx.lifecycle.ViewModel
import com.jangjh123.shallwegoforawalk.data.repository.SplashRepository
import com.jangjh123.shallwegoforawalk.util.CoroutineScopes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: SplashRepository
) : ViewModel() {

    fun getRegistration(onComplete: (Boolean) -> Unit) {
        CoroutineScopes.io {
            repository.isRegistered().collect { isRegistered ->
                if (isRegistered) {
                    onComplete(true)
                } else {
                    onComplete(false)
                }
            }
        }
    }
}