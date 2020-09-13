package com.hackademy.monetrix.data.repository

import androidx.lifecycle.LiveData
import com.hackademy.monetrix.data.dao.EntryDao
import com.hackademy.monetrix.data.dao.TransactionDao
import com.hackademy.monetrix.data.model.Entry
import com.hackademy.monetrix.data.model.Transaction
import java.time.LocalDate
import java.util.*

class TransactionRepository(private val entryDao: EntryDao,
                            private val transactionDao: TransactionDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val transactions: LiveData<List<Transaction>> = transactionDao.getAll()

    suspend fun insert(entry: Entry) {
        entryDao.insertAll(entry)
    }

    fun getTransactionsInThisMonth(): LiveData<List<Transaction>> {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_MONTH, 1)
        val startDate = Date(cal.time.time)
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
        val endDate = Date(cal.time.time)
        val transactionsThisMonth = transactionDao.getAllBetweenDates(startDate, endDate)
        return transactionsThisMonth
    }
}