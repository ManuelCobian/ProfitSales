package com.luvsoft.room.network.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ProfitEntity")
data class ProfitEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var profit: Double = 0.0,
    var name: String = "",
    val type: Long = 0,
    val isOneTime: Boolean = false
)
