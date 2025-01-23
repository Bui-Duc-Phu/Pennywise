package com.example.pennywise.data.repository

import com.example.pennywise.data.dao.ExpenseDao
import com.example.pennywise.data.entity.ExpenseEntity
import javax.inject.Inject

class ExpenseRepository @Inject constructor(private val expenseDao: ExpenseDao) {

    suspend fun getAllExpenses(): List<ExpenseEntity> {
        return expenseDao.getAllExpenses()
    }

    suspend fun getExpensesByDate(date: String): List<ExpenseEntity> {
        return expenseDao.getExpensesByDate(date)
    }

    suspend fun insertExpense(expense: ExpenseEntity) {
        expenseDao.insertExpense(expense)
    }

    suspend fun deleteExpense(id: Int) {
        expenseDao.deleteExpense(id)
    }
}
