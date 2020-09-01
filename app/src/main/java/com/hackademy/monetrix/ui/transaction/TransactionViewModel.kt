package com.hackademy.monetrix.ui.transaction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hackademy.monetrix.data.database.AppDatabase
import com.hackademy.monetrix.data.model.Entry
import com.hackademy.monetrix.data.model.Transaction
import com.hackademy.monetrix.data.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TransactionRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val transactions: LiveData<List<Transaction>>

    init {
        val database = AppDatabase.getDatabase(application,viewModelScope)
        val transactionDao = database.transactionDao()
        val entryDao = database.entryDao()
        repository = TransactionRepository(entryDao, transactionDao)
        transactions = repository.transactions
    }
}