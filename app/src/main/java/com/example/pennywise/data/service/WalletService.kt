package com.example.pennywise.data.service

import com.example.pennywise.data.dao.ExpenseDao
import com.example.pennywise.data.entity.WalletEntity
import com.example.pennywise.data.repository.ExpenseRepository
import com.example.pennywise.data.repository.WalletRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WalletService @Inject constructor(
    private val walletRepository: WalletRepository,
    private val expenseRepository: ExpenseRepository
) {

    suspend fun calculateRemainingBudget(month: String): String? {
        val wallet = walletRepository.getWalletByMonth(month)
        return wallet?.let {
            val salary = it.totalSalary.toDoubleOrNull() ?: 0.0
            val expense = it.totalExpense.toDoubleOrNull() ?: 0.0
            (salary - expense).toString()
        }
    }

    suspend fun insertWallet(wallet: WalletEntity) {
        walletRepository.insertWallet(wallet)
    }

    suspend fun getWalletByMoth(month: String) : WalletEntity? {
       return walletRepository.getWalletByMonth(month)
    }


    suspend fun updateTotalExpense(month: String, newTotalExpense: Double) {
        walletRepository.updateTotalExpense(month, newTotalExpense)
    }

   suspend fun observeTotalExpense(month: String): Flow<Double?> {
        return expenseRepository.getTotalExpenseByMonth(month)
    }

    suspend fun isMonthExists(month: String): Flow<Double?> {
        return expenseRepository.getTotalExpenseByMonth(month)
    }

}