package com.luvsoft.room.network.services

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.luvsoft.room.network.models.ExpensesEntity
import com.luvsoft.room.network.models.ProfitEntity

@Dao
interface ProfitGuardDoo {

    @Query("SELECT SUM(profit) FROM ExpensesEntity")
    suspend fun getTotalExpensesAll(): Double?

    @Query("SELECT SUM(profit) FROM ProfitEntity")
    suspend fun getTotalAllProfit(): Double?

    @Query("SELECT * FROM ExpensesEntity WHERE type = :type")
    suspend fun getAllExpenses(type: Long): List<ExpensesEntity>

    @Query("SELECT SUM(profit) FROM ExpensesEntity WHERE type = :type")
    suspend fun getTotalExpenses(type: Long): Double?

    @Insert
    suspend fun insertExpenses(expensesEntity: ExpensesEntity): Long

    @Update
    suspend fun updateExpenses(expensesEntity: ExpensesEntity)

    @Delete
    suspend fun deleteExpenses(expensesEntity: ExpensesEntity)

    @Query("SELECT * FROM ProfitEntity WHERE type = :type")
    suspend fun getAllProfit(type: Long): List<ProfitEntity>

    @Query("SELECT SUM(profit) FROM ProfitEntity WHERE type = :type")
    suspend fun getTotalProfit(type: Long): Double?

    @Insert
    suspend fun insertProfit(expensesEntity: ProfitEntity): Long

    @Update
    suspend fun updateProfit(expensesEntity: ProfitEntity)

    @Delete
    suspend fun deleteProfit(expensesEntity: ProfitEntity)
}