package com.example.pennywise.Adapter.model

data class Message(
    val content: String,
    val timestamp: String,
    val isSentByMe: Boolean,
    val isTyping: Boolean,
) {

}