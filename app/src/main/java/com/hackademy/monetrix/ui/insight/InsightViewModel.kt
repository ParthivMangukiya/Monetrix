package com.hackademy.monetrix.ui.insight

import android.app.Application
import androidx.lifecycle.*
import com.hackademy.monetrix.data.database.AppDatabase
import com.hackademy.monetrix.data.model.CategoryTotal
import com.hackademy.monetrix.data.model.EntryTotal
import com.hackademy.monetrix.data.model.EntryTotalWithMonth
import com.hackademy.monetrix.data.model.EntryType
import com.hackademy.monetrix.data.repository.CategoryRepository
import com.hackademy.monetrix.data.repository.TransactionRepository

class InsightViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CategoryRepository
    private val transactionRepository: TransactionRepository
    val showEntryType: MutableLiveData<EntryType> = MutableLiveData(EntryType.Income)
    var categoriesTotal: LiveData<List<CategoryTotal>>
    var entryTotal: LiveData<List<EntryTotalWithMonth>>

    init {
        val database = AppDatabase.getDatabase(application, viewModelScope)
        val categoryDao = database.categoryDao()
        val transactionDao = database.transactionDao()
        repository = CategoryRepository(categoryDao)
        transactionRepository = TransactionRepository(transactionDao)
        categoriesTotal = showEntryType.switchMap {
            liveData {
                emitSource(repository.getCategoriesTotalOfThisMonth(it))
            }
        }

        entryTotal = showEntryType.switchMap {
            liveData {
                emitSource(transactionRepository.getEntryTotalByMonth(it))
            }
        }
    }
}