package com.hackademy.monetrix.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    val name: String,
    val image: String
) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}
