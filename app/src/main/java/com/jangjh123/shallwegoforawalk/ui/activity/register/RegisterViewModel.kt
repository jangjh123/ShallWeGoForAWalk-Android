package com.jangjh123.shallwegoforawalk.ui.activity.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jangjh123.shallwegoforawalk.data.model.DogListTypes
import com.jangjh123.shallwegoforawalk.data.model.FurType
import com.jangjh123.shallwegoforawalk.data.model.Size
import com.jangjh123.shallwegoforawalk.data.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository
) :
    ViewModel() {

    private val _dogName = MutableLiveData<String>()
    val dogName: LiveData<String>
        get() = _dogName

    private val _dogGender = MutableLiveData<Boolean>()
    private val dogGender: LiveData<Boolean>
        get() = _dogGender

    private val _dogAge = MutableLiveData<Int>()
    val dogAge: LiveData<Int>
        get() = _dogAge

    private val _dogFurType = MutableLiveData<FurType>()
    private val dogFurType: LiveData<FurType>
        get() = _dogFurType

    private val _dogSize = MutableLiveData<Size>()
    private val dogSize: LiveData<Size>
        get() = _dogSize

    private val _infoCount = MutableLiveData(0)
    val infoCount: LiveData<Int>
        get() = _infoCount

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


    fun setDogFurType(furType: FurType) {
        if (dogFurType.value == null) {
            _infoCount.postValue(_infoCount.value!! + 1)
        }
        _dogFurType.postValue(furType)
    }

    fun setSize(size: Size) {
        if (dogSize.value == null) {
            _infoCount.postValue(_infoCount.value!! + 1)
        }

        _dogSize.postValue(size)
    }

    fun storeDog() {
//        CoroutineScope(dispatcher).launch {
//            repository.storeIntoRoom(
//                DogListTypes.Dog(
//                    0,
//                    dogName.value!!,
//                    dogGender.value!!,
//                    dogAge.value!!,
//                    dogFurType.value!!,
//                    dogSize.value!!,
//                    ""
//                )
//            )
//        }
    }
}