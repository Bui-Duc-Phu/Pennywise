package com.example.pennywise.ulti

object Mapper {

    fun formatNumber(input: String): String {

        val input = input.toDoubleOrNull()?.toLong() ?: 0L


        val num = input.toString().toLongOrNull() ?: return "Invalid number"

        return when {
            num < 100_000 -> "%,d".format(num).replace(',', '.')
            num < 1_000_000 -> "${num / 1_000}K"
            else -> {
                val trPart = num / 1_000_000
                val kPart = (num % 1_000_000) / 1_000
                if (kPart > 0) "${trPart}tr${kPart}K" else "${trPart}tr"
            }
        }
    }

}