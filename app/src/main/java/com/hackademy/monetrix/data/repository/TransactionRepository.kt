package com.hackademy.monetrix.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.hackademy.monetrix.data.dao.TransactionDao
import com.hackademy.monetrix.data.model.*
import com.hackademy.monetrix.util.UIUtil.combineAndCompute
import java.time.LocalDate
import java.util.*

class TransactionRepository(private val transactionDao: TransactionDao) {

    // Room executes all queries
    @WorkerThread
    suspend fun insert(entry: Entry) {
        transactionDao.insertAll(entry)
    }

    @WorkerThread
    fun getTransactionsByEntryType(entryType: EntryType): LiveData<List<Transaction>> {
        return transactionDao.getAllByEntryType(entryType)
    }

    @WorkerThread
    fun getAll(): LiveData<List<Transaction>> {
        return transactionDao.getAll()
    }

    @WorkerThread
    fun getTransactionsInThisMonth(): LiveData<List<Transaction>> {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_MONTH, 1)
        val startDate = Date(cal.time.time)
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
        val endDate = Date(cal.time.time)
        val transactionsThisMonth = transactionDao.getAllBetweenDates(startDate, endDate)
        return transactionsThisMonth
    }

    @WorkerThread
    fun getEntryTotalOfThisMonth(): LiveData<List<EntryTotal>> {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_MONTH, 1)
        val startDate = Date(cal.time.time)
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
        val endDate = Date(cal.time.time)
        return transactionDao.getTotalBetweenDates(startDate, endDate)
    }

    @WorkerThread
    fun getBalance(): LiveData<Double> {
        return transactionDao.getTotal(EntryType.Income)
            .combineAndCompute(transactionDao.getTotal(EntryType.Expense)) { a, b ->
                a - b
            }
    }
}