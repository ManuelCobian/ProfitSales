package com.luvsoft.profitsales.ui.adapters.swap

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.luvsoft.profitsales.ui.adapters.ExpensesAdapter
import javax.inject.Inject

class SwipeToDeleteExpensesCallback @Inject constructor(private val adapter: ExpensesAdapter) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (direction == ItemTouchHelper.LEFT) {
            // Accede al índice del ítem deslizado
            val position = viewHolder.adapterPosition
            // Realiza la acción deseada con el ítem deslizado
            adapter.performSwipeAction(position)
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        // Personaliza el dibujo del ítem si es necesario
    }
}