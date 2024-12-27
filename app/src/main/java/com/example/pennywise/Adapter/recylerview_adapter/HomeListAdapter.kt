package com.example.pennywise.Adapter.recylerview_adapter

import android.content.Context
import com.example.pennywise.Adapter.BaseAdapter
import com.example.pennywise.Adapter.model.TabLabel
import com.example.pennywise.databinding.ItemViewHomeBinding
import javax.inject.Inject

class HomeListAdapter(
    private val context: Context, // Context is passed from the outside
    private val items: List<TabLabel>
) : BaseAdapter<TabLabel, ItemViewHomeBinding>(
    context,
    items,
    ItemViewHomeBinding::inflate
) {
    override fun bind(binding: ItemViewHomeBinding, item: TabLabel, position: Int) {
        // Your binding logic here
    }
}



