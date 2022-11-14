package com.jangjh123.shallwegoforawalk.ui.fragment.dog_list

import androidx.lifecycle.ViewModel
import com.jangjh123.shallwegoforawalk.data.model.weather.Dog
import com.jangjh123.shallwegoforawalk.data.repository.DogListRepository
import com.jangjh123.shallwegoforawalk.util.CoroutineScopes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DogListViewModel @Inject constructor(private val repository: DogListRepository) :
    ViewModel() {

    fun removeDog(id: Int, onNoDog: () -> Unit, onRefresh: () -> Unit) {
        repository.removeDogById(id, onComplete = {
            CoroutineScopes.io {
                repository.getNewDogList().collect { dogs ->
                    if (dogs.isEmpty()) {
                        repository.setRegistered()
                        onNoDog()
                    } else {
                        onRefresh()
                    }
                }
            }
        })
    }
}