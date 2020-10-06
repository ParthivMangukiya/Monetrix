package com.hackademy.monetrix.ui.add

import android.app.Application
import android.widget.Toast
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
import java.text.SimpleDateFormat
import java.util.*

class AddViewModel(application: Application) : AndroidViewModel(application) {

    private val transactionRepository: TransactionRepository
    private val categoryRepository: CategoryRepository
    val _amount = MutableLiveData<String>("0")
    val _description = MutableLiveData<String>("")
    val _incomeExpense = MutableLiveData<Boolean>(false)
    val _date = MutableLiveData<Date>(Date(Calendar.getInstance().time.time))
    val saved = MutableLiveData<Boolean>(false)
    val pickupDate = MutableLiveData<Boolean>(false)


    val amount: LiveData<String> = _amount
    val description: LiveData<String> = _description
    val date: LiveData<Date> = _date
    val formattedDate: LiveData<String> = map(_date) {
        SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(it)
    }
    var categoryId: Long = 0
    var entryType: LiveData<EntryType> = map(_incomeExpense) {
        when (it) {
            false -> EntryType.Investment
            true -> EntryType.Expense
        }
    }

    init {
        val database = AppDatabase.getDatabase(application, viewModelScope)
        val transactionDao = database.transactionDao()
        val categoryDao = database.categoryDao()
        transactionRepository = TransactionRepository(transactionDao)
        categoryRepository = CategoryRepository(categoryDao)
    }

    fun getCategories(): LiveData<List<Category>> {
        return categoryRepository.categories
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert() = viewModelScope.launch(Dispatchers.IO) {
        val entry = Entry(
            description = description.value!!,
            amount = amount.value!!.toDouble(),
            date = date.value!!,
            type = entryType.value!!,
            categoryId = categoryId
        )
        transactionRepository.insert(entry)
        viewModelScope.launch(Dispatchers.Main) {
            saved.value = true
        }
    }

    fun onClick(categoryId: Long) {
        this.categoryId = categoryId
    }

    fun pickupDate() {
        pickupDate.value = true
    }
}