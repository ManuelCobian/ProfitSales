package com.luvsoft.profitsales.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.extensions.toMexican
import com.luvsoft.profitsales.R
import com.luvsoft.profitsales.databinding.CardSalesActionBinding
import com.luvsoft.profitsales.ui.adapters.diff.ExpensesDiff
import com.luvsoft.room.network.models.ExpensesEntity
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ExpensesAdapter @Inject constructor(
    @ActivityContext private val context: Context,
    private val diff: ExpensesDiff,
    val callbackClick: CallbackClick
) : ListAdapter<ExpensesEntity, RecyclerView.ViewHolder>(diff) {

    interface CallbackClick {
        fun onItemClicked(dep: ExpensesEntity)
    }

    fun performSwipeAction(position: Int) {
        val item = getItem(position)
        callbackClick.onItemClicked(item)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val binding = CardSalesActionBinding.bind(view)

        init {

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            // Obtiene la posición del elemento clicado y llama al método onItemClick del listener
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val item = getItem(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.card_sales_action, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val profit = getItem(position)

        with((holder as ViewHolder).binding) {
            tvName.text = profit.name
            tvPrice.text =
                context.resources.getString(com.luvsoft.base.R.string.convert_to_pesos, profit.profit.toMexican())
        }
    }
}