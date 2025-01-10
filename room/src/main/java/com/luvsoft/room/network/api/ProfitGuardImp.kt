package com.luvsoft.room.network.api

import com.luvsoft.room.network.models.ExpensesEntity
import com.luvsoft.room.network.models.ProfitEntity
import com.luvsoft.room.network.services.ProfitGuardDoo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfitGuardImp @Inject constructor(private val profitGuardDoo: ProfitGuardDoo):
    ProfitGuardApi {
    override suspend fun getSumaProfitAll(callback: (Double?) -> Unit) {
        callback(profitGuardDoo.getTotalAllProfit())
    }

    override suspend fun getSumaExpensesAll(callback: (Double?) -> Unit) {
        callback(profitGuardDoo.getTotalExpensesAll())
    }

    override suspend fun getAllProfit(callback: (List<ProfitEntity>) -> Unit, typeId: Long) {
        callback(profitGuardDoo.getAllProfit(typeId))
    }

    override suspend fun getSumaProfit(callback: (Double?) -> Unit, typeId: Long) {
        callback(profitGuardDoo.getTotalProfit(typeId))
    }

    override suspend fun addProfit(foodEntity: ProfitEntity, callback: (Long) -> Unit) {
        val result = profitGuardDoo.insertProfit(foodEntity)
        callback(result)
    }

    override suspend fun updateProfit(foodEntity: ProfitEntity) {
        profitGuardDoo.updateProfit(foodEntity)
    }

    override suspend fun deleteProfit(foodEntity: ProfitEntity) {
        profitGuardDoo.deleteProfit(foodEntity)
    }

    override suspend fun getAllExpenses(callback: (List<ExpensesEntity>) -> Unit, typeId: Long) {
        callback(profitGuardDoo.getAllExpenses(typeId))
    }

    override suspend fun getSumaExpenses(callback: (Double?) -> Unit, typeId: Long) {
        callback(profitGuardDoo.getTotalExpenses(typeId))
    }

    override suspend fun addExpenses(foodEntity: ExpensesEntity, callback: (Long) -> Unit) {
        val result = profitGuardDoo.insertExpenses(foodEntity)
        callback(result)
    }

    override suspend fun updateExpenses(foodEntity: ExpensesEntity) {
        profitGuardDoo.updateExpenses(foodEntity)
    }

    override suspend fun deleteExpenses(foodEntity: ExpensesEntity) {
        profitGuardDoo.deleteExpenses(foodEntity)
    }
}