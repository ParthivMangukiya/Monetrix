package com.hackademy.monetrix.data.model

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.*

data class SavingPlanTransaction(
    @Embedded var savingPlan: SavingPlan,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    var category: Category
) {
    @Ignore
    var savedAmount: Double = 0.0
}