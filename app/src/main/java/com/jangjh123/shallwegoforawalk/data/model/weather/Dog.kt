package com.jangjh123.shallwegoforawalk.data.model.weather

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jangjh123.shallwegoforawalk.data.model.FurType
import com.jangjh123.shallwegoforawalk.data.model.Size

@Entity
data class Dog(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val name: String,
    val gender: Boolean,
    var age: Int,
    val furType: FurType,
    val size: Size,
    var reason: String
)