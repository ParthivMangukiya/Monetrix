package com.hackademy.monetrix.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.hackademy.monetrix.data.dao.CategoryDao
import com.hackademy.monetrix.data.dao.TransactionDao
import com.hackademy.monetrix.data.model.*
import java.util.*

class CategoryRepository(private val categoryDao: CategoryDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val categories: LiveData<List<Category>> = categoryDao.getAll()

    @WorkerThread
    fun getCategoriesTotalOfThisMonth(entryType: EntryType): LiveData<List<CategoryTotal>> {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_MONTH, 1)
        val startDate = Date(cal.time.time)
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
        val endDate = Date(cal.time.time)
        val expenseByCategories =
            categoryDao.getCategoriesTotalBetweenDates(entryType, startDate, endDate)
        return expenseByCategories
    }
}