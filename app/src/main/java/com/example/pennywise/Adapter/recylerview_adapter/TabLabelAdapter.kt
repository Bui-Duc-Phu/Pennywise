package com.example.pennywise.Adapter.recylerview_adapter

import android.content.Context
import com.example.pennywise.Adapter.BaseAdapter
import com.example.pennywise.Adapter.model.TabLabel
import com.example.pennywise.databinding.TabLabelBinding // Đảm bảo import đúng lớp ViewBinding

class TabLabelAdapter(
    context: Context,
    private val items: List<TabLabel>
) : BaseAdapter<TabLabel, TabLabelBinding>(
    context,
    items,
    TabLabelBinding::inflate
) {
    override fun bind(binding: TabLabelBinding, item: TabLabel, position: Int) {
        binding.textView.text = item.title
        binding.tongluongEdt.text = item.amount
    }
}
