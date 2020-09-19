package com.hackademy.monetrix.ui.transaction

import android.app.Application
import androidx.lifecycle.*
import com.hackademy.monetrix.data.database.AppDatabase
import com.hackademy.monetrix.data.model.Entry
import com.hackademy.monetrix.data.model.EntryType
import com.hackademy.monetrix.data.model.Transaction
import com.hackademy.monetrix.data.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TransactionRepository

    var transactions: LiveData<List<Transaction>>
    var showIncome: MutableLiveData<Boolean> = MutableLiveData(true)

    init {
        val database = AppDatabase.getDatabase(application, viewModelScope)
        val transactionDao = database.transactionDao()
        repository = TransactionRepository(transactionDao)
        transactions = showIncome.switchMap {
            if (it) {
                liveData {
                    emitSource(repository.getTransactionsByEntryType(EntryType.Income))
                }
            } else {
                liveData {
                    emitSource(repository.getTransactionsByEntryType(EntryType.Expense))
                }
            }
        }
    }

}