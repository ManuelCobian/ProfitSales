package com.luvsoft.profitsales.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.luvsoft.profitsales.R
import com.luvsoft.profitsales.databinding.CardSalesBinding
import com.luvsoft.profitsales.network.FortnightsModel
import com.luvsoft.profitsales.ui.adapters.diff.ProfitTypeDiff
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ProfitTypeAdapter @Inject constructor(
    @ActivityContext private val context: Context,
    private val diff: ProfitTypeDiff,
    val callbackClick: CallbackClick
): ListAdapter<FortnightsModel, RecyclerView.ViewHolder>(diff)   {
    interface CallbackClick {
        fun onItemClicked(dep: FortnightsModel)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val binding = CardSalesBinding.bind(view)

        init {

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            // Obtiene la posición del elemento clicado y llama al método onItemClick del listener
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val item = getItem(position)
                callbackClick.onItemClicked(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.card_sales, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val topic = getItem(position)
        with((holder as ViewHolder).binding) {
            tvName.text = topic.name
            tvPrice.isGone = true
        }
    }
}