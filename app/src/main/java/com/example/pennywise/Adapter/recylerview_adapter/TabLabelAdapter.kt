package com.example.pennywise.Adapter.recylerview_adapter

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.pennywise.Adapter.BaseAdapter
import com.example.pennywise.Adapter.model.TabLabel
import com.example.pennywise.R
import com.example.pennywise.databinding.TabLabelBinding // Đảm bảo import đúng lớp ViewBinding

class TabLabelAdapter(
    context: Context,
    private val items: List<TabLabel>
) : BaseAdapter<TabLabel, TabLabelBinding>(
    context,
    items,
    TabLabelBinding::inflate
) {
    @SuppressLint("ResourceAsColor", "ResourceType")
    override fun bind(binding: TabLabelBinding, item: TabLabel, position: Int) {
        binding.textView.text = item.title
        binding.tongluongEdt.text = item.amount
        if(position % 2 != 0){
            binding.backgrMain.cardElevation = 0f
            binding.backgrMain.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.primary_blue2))
            binding.textView.setTextColor(ContextCompat.getColor(getContext(), R.color.white))
            binding.tongluongEdt.setTextColor(ContextCompat.getColor(getContext(), R.color.white))
            binding.imageView2.setImageResource(R.drawable.wallet_white)
        }
    }

    override fun updateItems(newItems: List<TabLabel>) {
    }
}
