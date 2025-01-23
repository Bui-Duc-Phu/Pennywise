package com.example.pennywise.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pennywise.data.entity.Message
import com.example.pennywise.data.service.MessageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val messageService: MessageService) : ViewModel() {

    val messages: Flow<List<Message>> = messageService.getAllMessages()

    fun sendMessage(content: String, isSentByMe: Boolean, userName: String? = null) {
        viewModelScope.launch {
            messageService.sendMessage(content, isSentByMe, userName)
        }
    }

    fun clearMessages() {
        viewModelScope.launch {
            messageService.clearMessages()
        }
    }
}
