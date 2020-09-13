package com.hackademy.monetrix.data.model

import androidx.room.Embedded
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.*

data class Transaction (
    @Embedded var entry: Entry,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    var category: Category
)