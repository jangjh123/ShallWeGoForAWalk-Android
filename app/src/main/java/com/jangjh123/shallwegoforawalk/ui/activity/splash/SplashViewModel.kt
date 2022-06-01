package com.jangjh123.shallwegoforawalk.ui.activity.splash

import com.jangjh123.shallwegoforawalk.data.repository.SplashRepository
import com.jangjh123.shallwegoforawalk.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    val repository: SplashRepository,
    val dispatcher: CoroutineDispatcher
) : BaseViewModel() {

    inline fun getRegistration(crossinline onComplete: (Boolean) -> Unit) {
        CoroutineScope(dispatcher).launch {
            repository.readRegistration().collect {
                onComplete(it)
            }
        }
    }
}