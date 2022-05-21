package com.jangjh123.shallwegoforawalk.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Dog(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val name: String)