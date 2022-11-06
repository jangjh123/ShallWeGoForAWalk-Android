package com.jangjh123.shallwegoforawalk.ui.activity.splash

import androidx.lifecycle.ViewModel
import com.jangjh123.shallwegoforawalk.data.repository.SplashRepository
import com.jangjh123.shallwegoforawalk.util.CoroutineScopes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    val repository: SplashRepository
) : ViewModel() {

    inline fun getRegistration(crossinline onComplete: (Boolean) -> Unit) {
        CoroutineScopes.io {
            repository.getDogList().collect {
                if (it == null) {
                    onComplete(false)

                } else {
                    onComplete(true)
                }
            }


        }
    }
}