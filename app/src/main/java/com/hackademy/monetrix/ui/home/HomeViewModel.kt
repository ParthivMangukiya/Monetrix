package com.hackademy.monetrix.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.hackademy.monetrix.data.database.AppDatabase
import com.hackademy.monetrix.data.model.CategoryTotal
import com.hackademy.monetrix.data.model.EntryType
import com.hackademy.monetrix.data.model.Transaction
import com.hackademy.monetrix.data.repository.CategoryRepository
import com.hackademy.monetrix.data.repository.TransactionRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CategoryRepository
    val expenseByCategories: LiveData<List<CategoryTotal>>
    val incomeByCategories: LiveData<List<CategoryTotal>>

    init {
        val database = AppDatabase.getDatabase(application, viewModelScope)
        val categoryDao = database.categoryDao()
        repository = CategoryRepository(categoryDao)
        expenseByCategories = repository.getCategoriesTotalOfThisMonth(EntryType.Expense)
        incomeByCategories = repository.getCategoriesTotalOfThisMonth(EntryType.Income)
    }
}