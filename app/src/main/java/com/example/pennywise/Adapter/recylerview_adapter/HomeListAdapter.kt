package com.example.pennywise.Adapter.recylerview_adapter

import android.content.Context
import com.example.pennywise.Adapter.BaseAdapter
import com.example.pennywise.Adapter.model.HomeList
import com.example.pennywise.Adapter.model.TabLabel
import com.example.pennywise.databinding.ItemViewHomeBinding
import javax.inject.Inject

class HomeListAdapter(
    private val context: Context, // Context is passed from the outside
    private val items: List<HomeList>
) : BaseAdapter<HomeList, ItemViewHomeBinding>(
    context,
    items,
    ItemViewHomeBinding::inflate
) {
    override fun bind(binding: ItemViewHomeBinding, item: HomeList, position: Int) {
        // Your binding logic here
    }

    override fun updateItems(newItems: List<HomeList>) {
    }
}



