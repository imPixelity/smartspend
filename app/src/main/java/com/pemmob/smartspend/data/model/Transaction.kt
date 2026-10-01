package com.pemmob.smartspend.data.model

data class Transaction(
    val id: String = "",
    val title: String = "",
    val amount: Double = 0.0,
    val type: String = "EXPENSE", // "INCOME" atau "EXPENSE"
    val category: String = "",
    val date: String = ""
)
