package com.example.pennywise.chatNetwork

data class DeepSeekRequest(
    val messages: List<Message>,
    val model: String = "deepseek-chat",
    val max_tokens: Int = 100,
    val temperature: Double = 1.0
)

data class Message(
    val role: String,
    val content: String
)

data class DeepSeekResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: MessageContent
)

data class MessageContent(
    val role: String,
    val content: String
)
