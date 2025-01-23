package com.example.pennywise.data.service

import com.example.pennywise.data.entity.WalletEntity
import com.example.pennywise.data.repository.WalletRepository
import javax.inject.Inject

class WalletService @Inject constructor(private val walletRepository: WalletRepository) {

    suspend fun calculateRemainingBudget(month: String): Double? {
        val wallet = walletRepository.getWalletByMonth(month)
        return wallet?.let {
            it.totalSalary - it.totalExpense
        }
    }

    suspend fun insertWallet(wallet: WalletEntity) {
        walletRepository.insertWallet(wallet)
    }

    suspend fun GetWalletByMoth(month: String) : WalletEntity? {
       return walletRepository.getWalletByMonth(month)
    }







}
