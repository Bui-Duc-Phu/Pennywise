package com.example.pennywise.data.dao

import androidx.room.*
import com.example.pennywise.data.entity.WalletEntity

@Dao
interface WalletDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallet(wallet: WalletEntity)

    @Query("SELECT * FROM wallet_table WHERE month = :month LIMIT 1")
    suspend fun getWalletByMonth(month: String): WalletEntity?

    @Query("UPDATE wallet_table SET totalExpense = :totalExpense WHERE month = :month")
    suspend fun updateTotalExpense(month: String, totalExpense: Double)
}
