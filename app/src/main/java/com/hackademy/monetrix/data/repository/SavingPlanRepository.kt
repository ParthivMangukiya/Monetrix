package com.hackademy.monetrix.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.hackademy.monetrix.data.dao.SavingPlanDao
import com.hackademy.monetrix.data.dao.TransactionDao
import com.hackademy.monetrix.data.model.*
import com.hackademy.monetrix.util.Util.combineAndCompute

class SavingPlanRepository(
    private val savingPlanDao: SavingPlanDao,
    private val transactionDao: TransactionDao
) {

    @WorkerThread
    suspend fun insert(vararg savingPlans: SavingPlan) {
        savingPlanDao.insertAll(*savingPlans)
    }

    @WorkerThread
    fun getSavingPlans(): LiveData<List<SavingPlanTransaction>> {
        val balance = transactionDao.getTotal(EntryType.Income)
            .combineAndCompute(transactionDao.getTotal(EntryType.Expense)) { a, b ->
                a - b
            }
        return balance.combineAndCompute(savingPlanDao.getAll()) { bal, list ->
            list.map {

                it.savedAmount = (bal * it.savingPlan.percent) / 100
                if (it.savedAmount >= it.savingPlan.amount) {
                    it.savedAmount = it.savingPlan.amount
                }
                if (it.savedAmount < 0) {
                    it.savedAmount = 0.0
                }
            }
            list
        }
    }

    @WorkerThread
    fun getCurrentSavingPlans(): LiveData<List<SavingPlanTransaction>> {
        val balance = transactionDao.getTotal(EntryType.Income)
            .combineAndCompute(transactionDao.getTotal(EntryType.Expense)) { a, b ->
                a - b
            }
        return balance.combineAndCompute(savingPlanDao.getAllCurrentPlans()) { bal, list ->
            list.map {
                it.savedAmount = (bal * it.savingPlan.percent) / 100
                if (it.savedAmount >= it.savingPlan.amount) {
                    it.savedAmount = it.savingPlan.amount
                }
                if (it.savedAmount < 0) {
                    it.savedAmount = 0.0
                }
            }
            list
        }
    }

    @WorkerThread
    fun getPastSavingPlans(): LiveData<List<SavingPlanTransaction>> {
        val balance = transactionDao.getTotal(EntryType.Income)
            .combineAndCompute(transactionDao.getTotal(EntryType.Expense)) { a, b ->
                a - b
            }
        return balance.combineAndCompute(savingPlanDao.getAllPastPlans()) { bal, list ->
            list.map {

                it.savedAmount = (bal * it.savingPlan.percent) / 100
                if (it.savedAmount >= it.savingPlan.amount) {
                    it.savedAmount = it.savingPlan.amount
                }
                if (it.savedAmount < 0) {
                    it.savedAmount = 0.0
                }
            }
            list
        }
    }
}