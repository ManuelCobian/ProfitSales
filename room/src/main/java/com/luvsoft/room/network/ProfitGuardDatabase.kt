package com.luvsoft.room.network

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luvsoft.room.network.models.ExpensesEntity
import com.luvsoft.room.network.models.ProfitEntity
import com.luvsoft.room.network.services.ProfitGuardDoo

@Database(entities = [ProfitEntity::class, ExpensesEntity::class], version = 1)
abstract class ProfitGuardDatabase : RoomDatabase()  {
    abstract fun userDao(): ProfitGuardDoo
}