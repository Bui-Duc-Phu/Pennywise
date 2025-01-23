package com.example.pennywise.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallet_table")
data class WalletEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val totalSalary: Double, // Tổng lương
    val totalExpense: Double, // Tổng chi phí
    val month: String, // Tháng (VD: "01-2025")
)
