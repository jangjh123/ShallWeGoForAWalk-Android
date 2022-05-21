package com.jangjh123.shallwegoforawalk.ui.fragment.dog_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jangjh123.shallwegoforawalk.data.model.DogListTypes
import com.jangjh123.shallwegoforawalk.data.repository.DogListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogListViewModel @Inject constructor(
    private val repository: DogListRepository,
    dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _dogList = MutableLiveData<List<DogListTypes>>()
    val dogList: LiveData<List<DogListTypes>>
        get() = _dogList

    init {
        CoroutineScope(dispatcher).launch {
            _dogList.postValue(ArrayList<DogListTypes>().also {
                it.addAll(repository.readDogList())
                it.add(DogListTypes.AddDog(null))
            })
        }
    }


}