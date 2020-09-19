package com.hackademy.monetrix.ui.savingplan

import android.app.Application
import androidx.lifecycle.*
import com.hackademy.monetrix.data.database.AppDatabase
import com.hackademy.monetrix.data.model.EntryType
import com.hackademy.monetrix.data.model.SavingPlanTransaction
import com.hackademy.monetrix.data.repository.SavingPlanRepository

class SavingPlanViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SavingPlanRepository

    val savingPlans: LiveData<List<SavingPlanTransaction>>
    var showCurrent: MutableLiveData<Boolean> = MutableLiveData(true)

    init {
        val database = AppDatabase.getDatabase(application, viewModelScope)
        val transactionDao = database.transactionDao()
        val savingPlanDao = database.savingPlanDao()
        repository = SavingPlanRepository(savingPlanDao, transactionDao)
        savingPlans = showCurrent.switchMap {
            if (it) {
                liveData {
                    emitSource(repository.getCurrentSavingPlans())
                }
            } else {
                liveData {
                    emitSource(repository.getPastSavingPlans())
                }
            }
        }
    }
}