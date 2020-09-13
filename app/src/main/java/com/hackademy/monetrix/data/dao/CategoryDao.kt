package com.hackademy.monetrix.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hackademy.monetrix.data.model.Category
import com.hackademy.monetrix.data.model.CategoryTotal
import com.hackademy.monetrix.data.model.EntryType
import com.hackademy.monetrix.data.model.Transaction
import java.util.*

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

    @Query("SELECT category.*, SUM(amount) as total FROM `category` JOIN `entry` on category.id = entry.categoryId  WHERE entry.type = :entryType Group By category.id, category.name, category.image")
    fun getCategoriesTotal(entryType: EntryType): LiveData<List<CategoryTotal>>

    @Query("SELECT category.*, SUM(amount) as total FROM `category` JOIN `entry` on category.id = entry.categoryId WHERE entry.type = :entryType and date between :startDate and :endDate Group By category.id, category.name, category.image")
    fun getCategoriesTotalBetweenDates(
        entryType: EntryType,
        startDate: Date,
        endDate: Date
    ): LiveData<List<CategoryTotal>>

}