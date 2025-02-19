package com.example.pennywise.viewModel

import Date
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pennywise.Adapter.model.Message
import com.example.pennywise.chatNetwork.dto.apiRespone.ApiResponse
import com.example.pennywise.chatNetwork.dto.apiRespone.result.ExpenseResult
import com.example.pennywise.data.entity.ExpenseEntity
import com.example.pennywise.data.service.ExpenseService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val expenseService: ExpenseService
) : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> get() = _messages

    private val _isUserTurn = MutableStateFlow(true)
    val isUserTurn: StateFlow<Boolean> get() = _isUserTurn

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _isTyping = MutableStateFlow(false)
    val isTyping: StateFlow<Boolean> get() = _isTyping


    fun setLoadingState(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun canProcessApiResponse(): Boolean {
        return !_isUserTurn.value
    }

    // Hiển thị hiệu ứng nhập tin nhắn, chỉ thêm nếu không có "Đang nhập..." trong danh sách
    fun showTypingIndicator() {
        if (_isTyping.value) return // Nếu đã có hiệu ứng nhập, không thêm mới

        _isTyping.value = true
        val typingMessage = Message(
            "Đang nhập...", System.currentTimeMillis().toString(), false,
            isTyping = true
        )

        viewModelScope.launch {
            val currentMessages = _messages.value.toMutableList()
            if (currentMessages.none { it.isTyping }) {
                currentMessages.add(typingMessage)
                _messages.value = currentMessages
            }
        }
    }

    fun hideTypingIndicator() {
        _isTyping.value = false
        viewModelScope.launch {
            val currentMessages = _messages.value.toMutableList()
            currentMessages.removeAll { it.isTyping }
            _messages.value = currentMessages
        }
    }

    fun addMessageFromUser(content: String, timestamp: String) {
        if (!_isUserTurn.value) return

        val newMessage = Message(
            content = content, timestamp = timestamp, isSentByMe = true,
            isTyping = false
        )

        viewModelScope.launch {
            updateMessageList(newMessage)
            _isUserTurn.value = false

            kotlinx.coroutines.delay(500)
            showTypingIndicator()
        }
    }


    suspend fun addMessageFromOther(api: ApiResponse<List<ExpenseResult>>) {
        println("call 111")
        val timestamp = Date.getCurrentMonthFormat()
        if (_isUserTurn.value) return
        val newMessage = Message(
            content = api.messages ?: "Không có nội dung",
            timestamp = timestamp,
            isSentByMe = false,
            isTyping = false
        )
        val expenses = api.result?.map { expense ->
            ExpenseEntity(
                date = timestamp,
                attribute = expense.attribute,
                price = expense.price,
                shop = expense.shop,
            )
        }
        withContext(Dispatchers.IO) {
            expenses?.forEach { expenseService.addExpense(it) }
        }

        hideTypingIndicator()
        updateMessageList(newMessage)
        _isUserTurn.value = true
        setLoadingState(false)
    }

    private fun updateMessageList(newMessage: Message) {
        viewModelScope.launch {
            val currentMessages = _messages.value.toMutableList()
            if (currentMessages.isNotEmpty() && currentMessages.last().content == newMessage.content) return@launch
            currentMessages.add(newMessage)
            _messages.value = currentMessages
        }
    }

}
