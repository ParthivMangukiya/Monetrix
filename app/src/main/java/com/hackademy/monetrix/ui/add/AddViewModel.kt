package com.hackademy.monetrix.ui.add

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.Transformations.map
import com.hackademy.monetrix.data.database.AppDatabase
import com.hackademy.monetrix.data.model.Category
import com.hackademy.monetrix.data.model.Entry
import com.hackademy.monetrix.data.model.EntryType
import com.hackademy.monetrix.data.repository.CategoryRepository
import com.hackademy.monetrix.data.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class AddViewModel(application: Application) : AndroidViewModel(application) {

    private val transactionRepository: TransactionRepository
    private val categoryRepository: CategoryRepository
    private val _amount = MutableLiveData<Double>(0.0)
    private val _incomeExpense = MutableLiveData<Boolean>(false)


    val amount: LiveData<Double> = _amount
    var entryType: LiveData<EntryType> = map(_incomeExpense) {
        when(it) {
            false -> EntryType.Income
            true -> EntryType.Expense
        }
    }

    init {
        val database = AppDatabase.getDatabase(application,viewModelScope)
        val transactionDao = database.transactionDao()
        val entryDao = database.entryDao()
        val categoryDao = database.categoryDao()
        transactionRepository = TransactionRepository(entryDao, transactionDao)
        categoryRepository = CategoryRepository(categoryDao)
    }

    fun getCategories(): LiveData<List<Category>> {
        return categoryRepository.categories
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert() = viewModelScope.launch(Dispatchers.IO) {
        val entry = Entry(amount = amount.value!!,date = Date(), type = entryType.value!!, categoryId = 1)
        transactionRepository.insert(entry)
    }
}