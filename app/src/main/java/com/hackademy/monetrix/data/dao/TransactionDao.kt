package com.hackademy.monetrix.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hackademy.monetrix.data.model.Entry
import com.hackademy.monetrix.data.model.EntryTotal
import com.hackademy.monetrix.data.model.EntryType
import com.hackademy.monetrix.data.model.Transaction
import java.util.*

@Dao
interface TransactionDao {
    @Insert
    suspend fun insertAll(vararg entry: Entry)

    @Delete
    suspend fun delete(entry: Entry)

    @Query("DELETE FROM `entry`")
    suspend fun deleteAll()

    @androidx.room.Transaction
    @Query("SELECT * FROM `entry`")
    fun getAll(): LiveData<List<Transaction>>

    @androidx.room.Transaction
    @Query("SELECT * FROM `entry` where type = :type")
    fun getAllByEntryType(type: EntryType): LiveData<List<Transaction>>

    @Query("SELECT SUM(Amount) FROM `entry` where type = :type")
    fun getTotal(type: EntryType): LiveData<Double>

    @androidx.room.Transaction
    @Query("SELECT * FROM `entry` where date between :startDate and :endDate")
    fun getAllBetweenDates(startDate: Date, endDate: Date): LiveData<List<Transaction>>

    @Query("SELECT type, SUM(amount) as total FROM `entry` where date between :startDate and :endDate group by type")
    fun getTotalBetweenDates(startDate: Date, endDate: Date): LiveData<List<EntryTotal>>
}