package com.example.pennywise.chatNetwork.dto.apiRespone

data class ApiResponse<T>(
    val status: String,
    val result: T,
    val messages: String
)