package com.example.pennywise.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pennywise.data.dao.ExpenseDao
import com.example.pennywise.data.dao.MessageDao
import com.example.pennywise.data.dao.WalletDao
import com.example.pennywise.data.entity.ExpenseEntity
import com.example.pennywise.data.entity.Message
import com.example.pennywise.data.entity.WalletEntity

@Database(entities = [
    WalletEntity::class,
    ExpenseEntity::class,
    Message::class
                     ], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun walletDao(): WalletDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Kiểm tra nếu instance đã tồn tại thì trả về instance
            return INSTANCE ?: synchronized(this) {
                // Nếu chưa, tạo mới một instance
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pennywise_database" // Tên của database
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
