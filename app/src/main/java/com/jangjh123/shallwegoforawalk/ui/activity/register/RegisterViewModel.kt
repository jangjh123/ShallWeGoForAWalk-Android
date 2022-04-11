package com.jangjh123.shallwegoforawalk.ui.activity.register

import androidx.lifecycle.ViewModel
import com.jangjh123.shallwegoforawalk.data.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegisterRepository) :
    ViewModel() {
    private var dogGender = true

    fun setGender(gender: Boolean) {
        dogGender = gender
    }
}