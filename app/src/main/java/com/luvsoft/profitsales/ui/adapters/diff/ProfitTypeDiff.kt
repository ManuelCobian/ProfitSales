package com.luvsoft.profitsales.ui.adapters.diff

import androidx.recyclerview.widget.DiffUtil
import com.luvsoft.profitsales.network.FortnightsModel
import javax.inject.Inject

class ProfitTypeDiff @Inject constructor(): DiffUtil.ItemCallback<FortnightsModel>() {
    override fun areItemsTheSame(oldItem: FortnightsModel, newItem: FortnightsModel): Boolean  = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: FortnightsModel, newItem: FortnightsModel): Boolean  = oldItem == newItem
}