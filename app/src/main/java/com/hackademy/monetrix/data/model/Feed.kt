package com.hackademy.monetrix.data.model

import androidx.room.Embedded
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.*

data class Feed(
    val image: String,
    val description: String
)