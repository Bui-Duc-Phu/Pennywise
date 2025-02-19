package com.example.pennywise.data.dao

import androidx.room.*
import com.example.pennywise.data.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM expense_table")
    suspend fun getAllExpenses(): List<ExpenseEntity>

    @Query("SELECT * FROM expense_table WHERE date LIKE :date")
    suspend fun getExpensesByDate(date: String): List<ExpenseEntity>

    @Query("DELETE FROM expense_table WHERE id = :id")
    suspend fun deleteExpense(id: Int)

    @Query("SELECT SUM(price) FROM expense_table WHERE date LIKE :month || '%'")
    fun getTotalExpenseByMonth(month: String): Flow<Double?>
}
