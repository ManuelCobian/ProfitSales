package com.luvsoft.profitsales.viewmodels

import android.content.Context
import androidx.annotation.CheckResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.extensions.biLet
import com.example.core.extensions.orDoubleZero
import com.luvsoft.profitsales.network.FortnightsModel
import com.luvsoft.profitsales.repositories.FortnightsRepository
import com.luvsoft.room.network.api.ProfitGuardApi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

const val MESSAGE_GREEN = "message_green"
const val MESSAGE_YELLOW = "message_yellow"
const val MESSAGE_ORANGE = "message_orange"
const val MESSAGE_DANGER = "message_danger"
const val MESSAGE_do = "message"

@HiltViewModel
internal class MainViewModels @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fortnightsRepository: FortnightsRepository,
    private val database: ProfitGuardApi
): ViewModel(){

    private val subModuleList = MutableLiveData<List<FortnightsModel>>()

    private val profitTotal = MutableLiveData<Double>()

    private val expensesTotal = MutableLiveData<Double>()

    private val evaluate = MutableLiveData<String>()

    fun start(){
        val list = fortnightsRepository.getAllType()
        subModuleList.value = list
    }

    suspend fun getTotal(){
        database.getSumaProfitAll { total ->
            profitTotal.postValue(total.orDoubleZero())
        }
        getTotalExpenses()
    }

    suspend fun getTotalExpenses(){
        database.getSumaExpensesAll { total ->
            expensesTotal.postValue(total.orDoubleZero())
        }
        getEvaluate()
    }

    private fun getEvaluate() {
        var total = MESSAGE_GREEN
        Pair(profitTotal.value, expensesTotal.value).biLet { profit, expenses ->
            val porcentajeGastos = (expenses / profit) * 100

            total = when {
                porcentajeGastos < 50 -> MESSAGE_GREEN
                porcentajeGastos < 75 -> MESSAGE_YELLOW
                porcentajeGastos < 90 -> MESSAGE_ORANGE
                porcentajeGastos >= 100 -> MESSAGE_DANGER
                else -> MESSAGE_do
            }
        }
        evaluate.postValue(total)
    }

    @CheckResult
    fun onSubModuleListChange(): LiveData<List<FortnightsModel>> = subModuleList


    @CheckResult
    fun onTotalProfit(): LiveData<Double> = profitTotal


    @CheckResult
    fun onEvaluate(): LiveData<String> = evaluate

    @CheckResult
    fun onTotalExpenses(): LiveData<Double> = expensesTotal
}