package com.luvsoft.profitsales.ui.adapters.diff

import androidx.recyclerview.widget.DiffUtil
import com.luvsoft.room.network.models.ExpensesEntity
import com.luvsoft.room.network.models.ProfitEntity
import javax.inject.Inject

class ExpensesDiff @Inject constructor(): DiffUtil.ItemCallback<ExpensesEntity>()  {
    override fun areItemsTheSame(oldItem: ExpensesEntity, newItem: ExpensesEntity) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ExpensesEntity, newItem: ExpensesEntity) = oldItem == newItem

}