package com.example.pennywise.data.repository

import com.example.pennywise.data.dao.WalletDao
import com.example.pennywise.data.entity.WalletEntity
import javax.inject.Inject

class WalletRepository @Inject constructor(private val walletDao: WalletDao) {

    suspend fun getWalletByMonth(month: String): WalletEntity? {
        return walletDao.getWalletByMonth(month)
    }

    suspend fun insertWallet(wallet: WalletEntity) {
        walletDao.insertWallet(wallet)
    }

    suspend fun updateTotalExpense(month: String, totalExpense: Double) {
        walletDao.updateTotalExpense(month, totalExpense)
    }

    suspend fun isMonthExists(month: String): Boolean {
        return walletDao.isMonthExists(month)
    }
}
