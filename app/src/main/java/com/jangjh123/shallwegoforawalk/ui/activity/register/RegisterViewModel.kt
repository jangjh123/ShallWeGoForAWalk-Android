package com.jangjh123.shallwegoforawalk.ui.activity.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jangjh123.shallwegoforawalk.data.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegisterRepository) :
    ViewModel() {

    private val _dogName = MutableLiveData<String>()
    val dogName: LiveData<String> = _dogName

    private val _dogGender = MutableLiveData<Boolean>()
    private val dogGender: LiveData<Boolean> = _dogGender

    private val _dogAge = MutableLiveData<Int>()
    val dogAge: LiveData<Int> = _dogAge

    private val _dogFurType = MutableLiveData<Int>()
    private val dogFurType: LiveData<Int> = _dogFurType

    private val _dogSize = MutableLiveData<Int>()
    private val dogSize: LiveData<Int> = _dogSize

    private val _infoCount = MutableLiveData(0)
    val infoCount: LiveData<Int> = _infoCount

    fun setName(name: String) {
        if (dogName.value == null) {
            _infoCount.postValue(_infoCount.value!! + 1)
        }
        _dogName.postValue(name)
    }

    fun setGender(gender: Boolean) {
        if (dogGender.value == null) {
            _infoCount.postValue(_infoCount.value!! + 1)
        }
        _dogGender.postValue(gender)
    }

    fun setAge(age: Int) {
        if (dogAge.value == null) {
            _infoCount.postValue(_infoCount.value!! + 1)
        }
        _dogAge.postValue(age)
    }


    fun setDogFurType(furType: Int) {
        if (dogFurType.value == null) {
            _infoCount.postValue(_infoCount.value!! + 1)
        }
        _dogFurType.postValue(furType)
    }

    fun setSize(size: Int) {
        if (dogSize.value == null) {
            _infoCount.postValue(_infoCount.value!! + 1)
        }
        _dogSize.postValue(size)
    }
}