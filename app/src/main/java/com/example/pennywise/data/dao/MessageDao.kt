package com.example.pennywise.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pennywise.data.entity.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Insert
    suspend fun insertMessage(message: Message)

    @Query("SELECT * FROM messages ORDER BY id ASC")
    fun getAllMessages(): Flow<List<Message>>

    @Query("DELETE FROM messages")
    suspend fun deleteAllMessages()
}
