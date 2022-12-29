package com.example.oneword

data class ResultGame(
    val status: Boolean,
    val word: String,
    val countAttempts: Int,
    val countTime: Double
)