package com.hackademy.monetrix.data.repository

import androidx.lifecycle.LiveData
import com.hackademy.monetrix.data.dao.CategoryDao
import com.hackademy.monetrix.data.dao.EntryDao
import com.hackademy.monetrix.data.dao.TransactionDao
import com.hackademy.monetrix.data.model.Category
import com.hackademy.monetrix.data.model.Entry
import com.hackademy.monetrix.data.model.Transaction

class CategoryRepository(private val categoryDao: CategoryDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val categories: LiveData<List<Category>> = categoryDao.getAll()

}