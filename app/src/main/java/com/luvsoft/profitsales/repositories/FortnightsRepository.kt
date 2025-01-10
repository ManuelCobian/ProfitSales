package com.luvsoft.profitsales.repositories

import com.luvsoft.profitsales.network.FortnightsModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FortnightsRepository @Inject constructor()  {
    private var type = 0L
    fun getAllType(): List<FortnightsModel> = listOf(
        FortnightsModel(id = 1, "Primera quincena"),
        FortnightsModel(id = 2, "Segunda quincena"),
    )

    fun getAllMenu(): List<FortnightsModel> = listOf(
        FortnightsModel(id = 1, "Ingresos"),
        FortnightsModel(id = 2, "Gastos"),
    )

    fun setTypeNow(typeSet: Long) {
        type = typeSet
    }

    fun getTypeNow(): Long = this.type
}