package com.example.pennywise.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pennywise.Adapter.model.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {

    // Danh sách tin nhắn
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> get() = _messages

    // Trạng thái kiểm soát luân phiên tin nhắn
    private val _isUserTurn = MutableStateFlow(true) // Bắt đầu với user gửi trước
    val isUserTurn: StateFlow<Boolean> get() = _isUserTurn


    fun canProcessApiResponse(): Boolean {
        return !_isUserTurn.value
    }


    // Thêm tin nhắn từ người dùng
    fun addMessageFromUser(content: String, timestamp: String) {
        if (!_isUserTurn.value) return // Chặn nếu không phải lượt của user

        val newMessage = Message(content = content, timestamp = timestamp, isSentByMe = true)
        updateMessageList(newMessage)
        _isUserTurn.value = false // Chuyển lượt sang API
    }

    // Thêm tin nhắn từ API (người khác)
    fun addMessageFromOther(content: String, timestamp: String) {
        if (_isUserTurn.value) return // Chặn nếu không phải lượt của API

        val newMessage = Message(content = content, timestamp = timestamp, isSentByMe = false)
        updateMessageList(newMessage)
        _isUserTurn.value = true // Chuyển lượt về user
    }

    private fun updateMessageList(newMessage: Message) {
        viewModelScope.launch {
            val currentMessages = _messages.value.toMutableList()
            if (currentMessages.none { it.content == newMessage.content && it.timestamp == newMessage.timestamp }) {
                currentMessages.add(newMessage)
                _messages.value = currentMessages
            }
        }
    }

    // Xóa toàn bộ tin nhắn
    fun clearMessages() {
        viewModelScope.launch {
            _messages.value = emptyList()
            _isUserTurn.value = true // Reset lượt về user
        }
    }
}
