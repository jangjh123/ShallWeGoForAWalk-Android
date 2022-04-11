package com.jangjh123.shallwegoforawalk.ui.activity.splash

import androidx.lifecycle.ViewModel
import com.jangjh123.shallwegoforawalk.data.repository.SplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    val repository: SplashRepository
) : ViewModel() {

    inline fun getRegistration(crossinline onComplete: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.readRegistration().collect {
                onComplete(it)
            }
        }
    }
}