package com.hackademy.monetrix.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class SavingPlan(
    val name: String,
    val amount: Double,
    val targetDate: Date,
    val categoryId: Long,
    val percent: Int,
    val date: Date,
    val completed: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}