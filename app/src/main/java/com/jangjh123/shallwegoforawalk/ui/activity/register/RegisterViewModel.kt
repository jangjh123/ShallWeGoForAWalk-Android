package com.jangjh123.shallwegoforawalk.ui.activity.register

import androidx.lifecycle.ViewModel
import com.jangjh123.shallwegoforawalk.data.model.FurType
import com.jangjh123.shallwegoforawalk.data.model.Size
import com.jangjh123.shallwegoforawalk.data.model.weather.Dog
import com.jangjh123.shallwegoforawalk.data.repository.RegisterRepository
import com.jangjh123.shallwegoforawalk.util.CoroutineScopes.io
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository
) : ViewModel() {
    fun storeDog(
        name: String,
        isBoy: Boolean,
        age: Int,
        furType: FurType,
        size: Size,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        runCatching {
            io {
                repository.setDog(
                    Dog(
                        0,
                        name,
                        isBoy,
                        age,
                        furType,
                        size,
                        ""
                    )
                )
            }
        }.onSuccess {
            setRegistered()
            onSuccess()
        }.onFailure {
            onFailure()
        }
    }

    private fun setRegistered() {
        io {
            repository.setRegistered()
        }
    }
}