package com.example.pennywise.data.service

import com.example.pennywise.data.entity.ExpenseEntity
import com.example.pennywise.data.repository.ExpenseRepository
import javax.inject.Inject

class ExpenseService @Inject constructor(private val expenseRepository: ExpenseRepository) {

    suspend fun addExpense(expense: ExpenseEntity) {
        expenseRepository.insertExpense(expense)
    }

    suspend fun getExpenses(): List<ExpenseEntity> {
        return expenseRepository.getAllExpenses()
    }
}
