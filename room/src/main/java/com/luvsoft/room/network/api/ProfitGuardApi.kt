package com.luvsoft.room.network.api

import com.luvsoft.room.network.models.ExpensesEntity
import com.luvsoft.room.network.models.ProfitEntity

interface ProfitGuardApi {

    suspend fun getSumaProfitAll(callback: (Double?) -> Unit)

    suspend fun getSumaExpensesAll(callback: (Double?) -> Unit)

    suspend fun getAllProfit(callback: (List<ProfitEntity>) -> Unit, typeId: Long)

    suspend fun getSumaProfit(callback: (Double?) -> Unit, typeId: Long)

    suspend fun addProfit(foodEntity: ProfitEntity, callback: (Long) -> Unit)

    suspend fun updateProfit(foodEntity: ProfitEntity)

    suspend fun deleteProfit(foodEntity: ProfitEntity)

    suspend fun getAllExpenses(callback: (List<ExpensesEntity>) -> Unit, typeId: Long)

    suspend fun getSumaExpenses(callback: (Double?) -> Unit, typeId: Long)

    suspend fun addExpenses(foodEntity: ExpensesEntity, callback: (Long) -> Unit)

    suspend fun updateExpenses(foodEntity: ExpensesEntity)

    suspend fun deleteExpenses(foodEntity: ExpensesEntity)
}