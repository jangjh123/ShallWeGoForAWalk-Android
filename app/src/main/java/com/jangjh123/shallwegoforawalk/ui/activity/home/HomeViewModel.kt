package com.jangjh123.shallwegoforawalk.ui.activity.home

import androidx.lifecycle.ViewModel
import com.jangjh123.shallwegoforawalk.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {
    fun getDogs() {
        repository.fetchDogs()
    }
}