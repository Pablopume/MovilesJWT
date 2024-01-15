package com.example.plantillaexamen.framework.pantalladetalle

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.plantillaexamen.R
import com.example.plantillaexamen.databinding.ViewOrderBinding
import com.example.plantillaexamen.framework.pantallamain.SwipeGesture
import com.example.restaurantapi.domain.modelo.Order


class OrderAdapter(
    val context: Context,
    val onDeleteAction: (Order) -> Unit
) :
    ListAdapter<Order, OrderAdapter.ItemViewHolder>(DiffCallback()) {

    private var selectedOrders = mutableSetOf<Order>()

    fun resetSelectMode() {
        selectedOrders.clear()
        notifyDataSetChanged()
    }

    fun setSelectedItems(selectedItems: List<Order>) {
        selectedOrders.clear()
        selectedOrders.addAll(selectedItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_order, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ViewOrderBinding.bind(itemView)

        fun bind(item: Order) {
            setupTextView(item)
            setupVisibility(item)
            itemView.setBackgroundColor(Color.WHITE)
        }

        private fun setupTextView(item: Order) {
            binding.tvId.text = item.id.toString()
            binding.tvNombre.text = item.orderDate.toString()
        }

        private fun setupVisibility(item: Order) {
            with(binding.selected) {
                visibility = if (selectedOrders.contains(item)) View.VISIBLE else View.GONE
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }

    val swipeGesture = object : SwipeGesture(context) {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            when (direction) {
                ItemTouchHelper.LEFT -> {
                    val position = viewHolder.bindingAdapterPosition
                    selectedOrders.remove(currentList[position])
                    onDeleteAction(currentList[position])
                }
            }
        }
    }
}
