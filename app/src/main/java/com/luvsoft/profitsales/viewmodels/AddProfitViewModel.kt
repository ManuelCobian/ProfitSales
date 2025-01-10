package com.luvsoft.profitsales.viewmodels

import android.content.Context
import androidx.annotation.CheckResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.extensions.biLet
import com.example.core.extensions.orDoubleZero
import com.example.core.extensions.orZero
import com.luvsoft.profitsales.network.FortnightsModel
import com.luvsoft.profitsales.repositories.FortnightsRepository
import com.luvsoft.room.network.api.ProfitGuardApi
import com.luvsoft.room.network.models.ExpensesEntity
import com.luvsoft.room.network.models.ProfitEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

enum class ProfitField { NAME_PROFIT, PROFIT_TOTAL }

@HiltViewModel
internal class AddProfitViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fortnightsRepository: FortnightsRepository,
    private val database: ProfitGuardApi
): ViewModel(){

    private val subModuleList = MutableLiveData<List<FortnightsModel>>()
    private var nameProfit = MutableLiveData("")
    private var totalProfit = MutableLiveData(0.0)
    private var period = MutableLiveData(0)
    private var error = MutableLiveData<Boolean>()
    private var enabled = MutableLiveData<Boolean>()

    fun init(period: Int){
        this.period.postValue(period)
        val list = fortnightsRepository.getAllType()
        subModuleList.value = list
    }

    fun updateField(value: String, profitFields: ProfitField) {
            when(profitFields){
                ProfitField.NAME_PROFIT -> nameProfit.value = value
                ProfitField.PROFIT_TOTAL -> totalProfit.value = value.toDouble()
            }
        validateEmptyFields()
    }

    private fun validateEmptyFields() {
        enabled.postValue(
            !nameProfit.value.isNullOrBlank() && totalProfit.value.orDoubleZero() > 0.0
        )
    }

    suspend fun saveProfit(){
        Pair(nameProfit.value, totalProfit.value).biLet { name, total ->
            val proft = ProfitEntity(
                name = name,
                profit = total,
                type = period.value.orZero().toLong()
            )
            database.addProfit(proft) { id ->
                error.postValue(id >= 1)
            }
        }
    }


    suspend fun saveExpense(){
        Pair(nameProfit.value, totalProfit.value).biLet { name, total ->
            val proft = ExpensesEntity(
                name = name,
                profit = total,
                type = period.value.orZero().toLong()
            )
            database.addExpenses(proft) { id ->
                error.postValue(id >= 1)
            }
        }
    }


    @CheckResult
    fun onErro(): LiveData<Boolean> = error

    @CheckResult
    fun onEnabled(): LiveData<Boolean> = enabled
}