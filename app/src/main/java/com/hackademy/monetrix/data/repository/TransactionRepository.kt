package com.hackademy.monetrix.data.repository

import androidx.lifecycle.LiveData
import com.hackademy.monetrix.data.dao.EntryDao
import com.hackademy.monetrix.data.dao.TransactionDao
import com.hackademy.monetrix.data.model.Entry
import com.hackademy.monetrix.data.model.Transaction

class TransactionRepository(private val entryDao: EntryDao,
                            private val transactionDao: TransactionDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val transactions: LiveData<List<Transaction>> = transactionDao.getAll()

    suspend fun insert(entry: Entry) {
        entryDao.insertAll(entry)
    }
}