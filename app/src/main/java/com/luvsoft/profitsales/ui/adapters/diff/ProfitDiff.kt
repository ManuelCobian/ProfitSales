package com.luvsoft.profitsales.ui.adapters.diff

import androidx.recyclerview.widget.DiffUtil
import com.luvsoft.room.network.models.ProfitEntity
import javax.inject.Inject

class ProfitDiff @Inject constructor(): DiffUtil.ItemCallback<ProfitEntity>()  {
    override fun areItemsTheSame(oldItem: ProfitEntity, newItem: ProfitEntity): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ProfitEntity, newItem: ProfitEntity) = oldItem == newItem
}