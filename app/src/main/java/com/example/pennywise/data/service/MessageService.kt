package com.example.pennywise.data.service

import com.example.pennywise.data.entity.Message
import com.example.pennywise.data.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MessageService @Inject constructor(private val repository: MessageRepository) {

    fun getAllMessages(): Flow<List<Message>> = repository.getAllMessages()

    suspend fun sendMessage(content: String, isSentByMe: Boolean, userName: String? = null) {
        val timestamp = System.currentTimeMillis().toString() // Lấy timestamp hiện tại
        val message = Message(
            content = content,
            timestamp = timestamp,
            isSentByMe = isSentByMe,
            userName = userName
        )
        repository.insertMessage(message)
    }

    suspend fun clearMessages() {
        repository.deleteAllMessages()
    }
}
