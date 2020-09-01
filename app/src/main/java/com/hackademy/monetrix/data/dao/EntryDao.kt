package com.hackademy.monetrix.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hackademy.monetrix.data.model.Entry

@Dao
interface EntryDao {
    @Insert
    suspend fun insertAll(vararg entry: Entry)

    @Delete
    suspend fun delete(entry: Entry)

    @Query("DELETE FROM `entry`")
    suspend fun deleteAll()
}