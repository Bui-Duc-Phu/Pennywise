package com.example.pennywise.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_table")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String, // Ngày sử dụng chi phí (sẽ được tính toán khi thêm mới)
    val attribute: String, // Tên khoản chi tiêu
    val price: Double, // Số tiền đã chi
    val shop: String // Cửa hàng đã chi tiêu
)
