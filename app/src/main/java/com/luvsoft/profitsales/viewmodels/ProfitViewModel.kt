package com.luvsoft.profitsales.viewmodels

import android.content.Context
import androidx.annotation.CheckResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.extensions.orDoubleZero
import com.luvsoft.profitsales.repositories.FortnightsRepository
import com.luvsoft.room.network.api.ProfitGuardApi
import com.luvsoft.room.network.models.ExpensesEntity
import com.luvsoft.room.network.models.ProfitEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
internal class ProfitViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: FortnightsRepository,
    private val database: ProfitGuardApi
): ViewModel() {

    private val subModuleList = MutableLiveData<List<ProfitEntity>>()
    private val salesModuleList = MutableLiveData<List<ExpensesEntity>>()
    private val profitTotal = MutableLiveData<Double>()
    private val expensesTotal = MutableLiveData<Double>()

    suspend fun getProfit(){
        database.getAllProfit(typeId = repository.getTypeNow(), callback = { list ->
            subModuleList.postValue(list)
        })
    }

    suspend fun getExpenses(){
        database.getAllExpenses(typeId = repository.getTypeNow(), callback = { list ->
            salesModuleList.postValue(list)
        })
    }

    suspend fun getTotal(){
        database.getSumaProfit(typeId = repository.getTypeNow(), callback = { list ->
            profitTotal.postValue(list.orDoubleZero())
        })
    }

    suspend fun getTotalExpenses(){
        database.getSumaExpenses(typeId = repository.getTypeNow(), callback = { list ->
            expensesTotal.postValue(list.orDoubleZero())
        })
    }

    suspend fun deleteProfit(profitEntity: ProfitEntity){
        database.deleteProfit(profitEntity)
        getProfit()
        getTotal()
    }

    suspend fun deleteExpenses(expensesEntity: ExpensesEntity){
        database.deleteExpenses(expensesEntity)
        getTotalExpenses()
        getExpenses()
    }


    protected fun setDismissListener(): () -> Unit = {}

    @CheckResult
    fun onSubModuleListChange(): LiveData<List<ProfitEntity>> = subModuleList

    @CheckResult
    fun onExpensesListChange(): LiveData<List<ExpensesEntity>> = salesModuleList


    @CheckResult
    fun onTotalProfit(): LiveData<Double> = profitTotal

    @CheckResult
    fun onTotalExpenses(): LiveData<Double> = expensesTotal
}