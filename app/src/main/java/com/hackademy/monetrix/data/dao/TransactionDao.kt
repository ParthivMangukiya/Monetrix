package com.hackademy.monetrix.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hackademy.monetrix.data.model.Entry
import com.hackademy.monetrix.data.model.Transaction

@Dao
interface TransactionDao {
    @androidx.room.Transaction
    @Query("SELECT * FROM `entry`")
    fun getAll(): LiveData<List<Transaction>>
}