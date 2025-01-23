package com.example.pennywise.data.repository

import com.example.pennywise.data.dao.MessageDao
import com.example.pennywise.data.entity.Message
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MessageRepository @Inject constructor(private val messageDao: MessageDao) {

    fun getAllMessages(): Flow<List<Message>> = messageDao.getAllMessages()

    suspend fun insertMessage(message: Message) {
        messageDao.insertMessage(message)
    }

    suspend fun deleteAllMessages() {
        messageDao.deleteAllMessages()
    }
}
