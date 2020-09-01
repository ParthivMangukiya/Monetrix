package com.hackademy.monetrix.data.model

import androidx.room.Embedded
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.*

data class Transaction (
    @Embedded val entry: Entry,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val category: Category
)