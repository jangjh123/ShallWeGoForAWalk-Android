package com.jangjh123.shallwegoforawalk.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

sealed class DogListTypes(var type: ListItemType) {
    @Entity
    data class Dog(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        val name: String,
        val gender: Boolean,
        var age: Int,
        val furType: Int,
        val size: Int
    ) : DogListTypes(ListItemType.DOG)

    data class AddDog(
        val text: String?
    ) : DogListTypes(ListItemType.ADD)
}

enum class ListItemType {
    DOG, ADD
}

