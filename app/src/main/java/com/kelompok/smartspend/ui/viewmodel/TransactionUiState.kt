package com.kelompok.smartspend.ui.viewmodel

import com.kelompok.smartspend.data.model.Transaction

// Daftar transaksi + ringkasan (Home)
sealed interface TransactionUiState {
    data object Loading : TransactionUiState
    data class Success(
        val transactions: List<Transaction>,
        val totalIncome: Double,
        val totalExpense: Double,
        val balance: Double          // totalIncome - totalExpense
    ) : TransactionUiState
    data class Error(val message: String) : TransactionUiState
}

// Satu transaksi (Detail)
sealed interface TransactionDetailUiState {
    data object Loading : TransactionDetailUiState
    data class Success(val transaction: Transaction) : TransactionDetailUiState
    data class Error(val message: String) : TransactionDetailUiState
}

// Hasil aksi tulis (tambah / hapus)
sealed interface TransactionActionState {
    data object Idle : TransactionActionState
    data object InProgress : TransactionActionState
    data object Success : TransactionActionState
    data class Error(val message: String) : TransactionActionState
}
