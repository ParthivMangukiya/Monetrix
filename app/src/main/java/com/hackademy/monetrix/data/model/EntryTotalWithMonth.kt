package com.hackademy.monetrix.data.model

import androidx.room.Embedded
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.*

data class EntryTotalWithMonth(
    val type: EntryType,
    val total: Double,
    var month: String
)