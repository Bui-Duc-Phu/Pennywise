package com.example.pennywise.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pennywise.data.dao.ExpenseDao
import com.example.pennywise.data.dao.WalletDao
import com.example.pennywise.data.entity.ExpenseEntity
import com.example.pennywise.data.entity.WalletEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Database(entities = [
    WalletEntity::class,
    ExpenseEntity::class,
                     ], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun walletDao(): WalletDao
    abstract fun expenseDao(): ExpenseDao
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
                )
                    .fallbackToDestructiveMigration() // cho phép xóa database cũ khi có sự thay doi trong entity
                    .addCallback(object : Callback() {       // cài đặt ban đầu cho bảng wallet
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                val walletDao = getDatabase(context).walletDao()
                                val currentMonth = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(Date())
                                val existingWallet = walletDao.getWalletByMonth(currentMonth)
                                if (existingWallet == null) {
                                    walletDao.insert(WalletEntity(totalSalary = "", totalExpense = "", month = currentMonth))
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
