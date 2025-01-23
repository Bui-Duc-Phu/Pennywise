package com.example.pennywise.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.pennywise.Adapter.model.TabLabel

abstract class BaseAdapter<T, VB : ViewBinding>(
    private val context: Context,
    private var items: List<T>,
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : RecyclerView.Adapter<BaseAdapter<T, VB>.BaseViewHolder>() {

    abstract fun bind(binding: VB, item: T, position: Int)

    // Hàm trừu tượng để cập nhật danh sách
    abstract fun updateItems(newItems: List<T>)

    fun updateInternalItems(newItems: List<T>) {
        items = newItems
        notifyDataSetChanged() // Thông báo RecyclerView làm mới giao diện
    }
    fun getContext(): Context {
        return context
    }

    inner class BaseViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = bindingInflater(inflater, parent, false)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        bind(holder.binding, items[position], position)
    }

    override fun getItemCount(): Int = items.size
}
