package com.kelompok.smartspend.data.model

data class Transaction(
    val id: String = "",
    val title: String = "",
    val amount: Double = 0.0,
    val type: String = "EXPENSE", // Nilai valid: "INCOME" atau "EXPENSE"
    val category: String = "",
    val date: String = ""
)

object TransactionType {
    const val INCOME = "INCOME"
    const val EXPENSE = "EXPENSE"
}

object TransactionCategories {
    val expense = listOf("Makanan", "Transportasi", "Belanja", "Tagihan", "Hiburan", "Lainnya")
    val income = listOf("Gaji", "Bonus", "Hadiah", "Lainnya")
}
