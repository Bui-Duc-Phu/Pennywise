package com.example.pennywise.Adapter.model

data class Message(
    val content: String,
    val timestamp: String,
    val isSentByMe: Boolean, // True nếu tin nhắn do người dùng gửi, False nếu do người khác gửi
    val userName: String? = null // Chỉ dùng cho tin nhắn của người khác
)