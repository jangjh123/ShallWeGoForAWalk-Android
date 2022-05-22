package com.jangjh123.shallwegoforawalk.ui.fragment.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jangjh123.shallwegoforawalk.data.model.DogListTypes.Dog
import com.jangjh123.shallwegoforawalk.data.repository.MainRepository
import com.jangjh123.shallwegoforawalk.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    dispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _dogList = MutableLiveData<List<Dog>>()
    val dogList: LiveData<List<Dog>>
        get() = _dogList

    init {
        CoroutineScope(dispatcher).launch {
            _dogList.postValue(repository.getDogList())
        }
    }
}