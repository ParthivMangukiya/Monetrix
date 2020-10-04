package com.hackademy.monetrix.data.model

import androidx.lifecycle.LiveData
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

enum class EntryType(val code: Int) {
    Income(0),
    Expense(1),
    Investment(2)
}

@Entity
data class Entry(
    val description: String,
    val amount: Double,
    val date: Date,
    val categoryId: Long,
    val type: EntryType
) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}