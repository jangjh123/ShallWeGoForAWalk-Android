package com.jangjh123.shallwegoforawalk.ui.activity.dog_list

import androidx.lifecycle.ViewModel
import com.jangjh123.shallwegoforawalk.data.model.DogsStateHandler
import com.jangjh123.shallwegoforawalk.data.repository.DogListRepository
import com.jangjh123.shallwegoforawalk.util.CoroutineScopes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DogListViewModel @Inject constructor(private val repository: DogListRepository) :
    ViewModel() {
    private val _dogsFlow = MutableStateFlow<DogsStateHandler?>(null)
    val dogsFlow: StateFlow<DogsStateHandler?>
        get() = _dogsFlow

    init {
        getDogList()
    }

    fun removeDog(id: Int) {
        repository.removeDogById(id, onComplete = {
            CoroutineScopes.io {
                getDogList()
            }
        })
    }

    private fun getDogList() {
        CoroutineScopes.io {
            repository.fetchDogList().collect { dogs ->
                if (dogs.isNotEmpty()) {
                    _dogsFlow.emit(
                        DogsStateHandler.Success(dogs)
                    )
                } else {
                    _dogsFlow.emit(
                        DogsStateHandler.Failure("List is empty.")
                    )
                }
            }
        }
    }

    fun setRegistered() {
        CoroutineScopes.io {
            repository.setRegistered()
        }
    }
}