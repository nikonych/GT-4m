package com.example.gt_4m.ui.home.swipe

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.gt_4m.R
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


abstract class SwipeTask(val context: Context?) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    val deleteColor = context?.let { ContextCompat.getColor(it, R.color.red) }
    val deleteIcon = R.drawable.ic_delete

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
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
        deleteColor?.let {
            RecyclerViewSwipeDecorator.Builder(
                c,
                recyclerView,
                viewHolder,
                dX,
                dY,
                actionState,
                isCurrentlyActive
            )
                .addSwipeLeftBackgroundColor(it)
                .addSwipeLeftActionIcon(deleteIcon)
                .create()
                .decorate()
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}