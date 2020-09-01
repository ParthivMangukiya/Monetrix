package com.hackademy.monetrix.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hackademy.monetrix.data.model.Category
import com.hackademy.monetrix.data.model.Transaction

@Dao
interface CategoryDao {

    @Query("SELECT * FROM `category`")
    fun getAll(): LiveData<List<Category>>

    @Insert
    suspend fun insertAll(vararg category: Category): Array<Long>

    @Delete
    suspend fun delete(category: Category)

    @Query("DELETE FROM `category`")
    suspend fun deleteAll()
}