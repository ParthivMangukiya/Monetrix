package com.hackademy.monetrix.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hackademy.monetrix.data.model.*
import java.util.*

@Dao
interface SavingPlanDao {
    @Insert
    suspend fun insertAll(vararg savingPlan: SavingPlan)

    @Delete
    suspend fun delete(savingPlan: SavingPlan)

    @Query("DELETE FROM `savingplan`")
    suspend fun deleteAll()

    @androidx.room.Transaction
    @Query("SELECT * FROM `savingplan`")
    fun getAll(): LiveData<List<SavingPlanTransaction>>

    @androidx.room.Transaction
    @Query("SELECT * FROM `savingplan` where completed = 0")
    fun getAllCurrentPlans(): LiveData<List<SavingPlanTransaction>>

    @androidx.room.Transaction
    @Query("SELECT * FROM `savingplan` where completed = 1")
    fun getAllPastPlans(): LiveData<List<SavingPlanTransaction>>
}