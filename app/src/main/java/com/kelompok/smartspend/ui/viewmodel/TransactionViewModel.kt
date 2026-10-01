package com.kelompok.smartspend.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompok.smartspend.data.model.Transaction
import com.kelompok.smartspend.data.model.TransactionType
import com.kelompok.smartspend.data.repository.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// STUB - akan diganti oleh Anggota C
class TransactionViewModel(
    private val repository: TransactionRepository = TransactionRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow<TransactionUiState>(TransactionUiState.Loading)
    val uiState: StateFlow<TransactionUiState> = _uiState.asStateFlow()

    private val _detailState = MutableStateFlow<TransactionDetailUiState>(TransactionDetailUiState.Loading)
    val detailState: StateFlow<TransactionDetailUiState> = _detailState.asStateFlow()

    private val _actionState = MutableStateFlow<TransactionActionState>(TransactionActionState.Idle)
    val actionState: StateFlow<TransactionActionState> = _actionState.asStateFlow()

    init {
        loadTransactions()
    }

    fun loadTransactions() {
        viewModelScope.launch {
            _uiState.value = TransactionUiState.Loading
            val result = repository.getTransactions()
            result.onSuccess { list ->
                val income = list.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
                val expense = list.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
                _uiState.value = TransactionUiState.Success(
                    transactions = list,
                    totalIncome = income,
                    totalExpense = expense,
                    balance = income - expense
                )
            }.onFailure { error ->
                _uiState.value = TransactionUiState.Error(error.message ?: "Gagal memuat transaksi")
            }
        }
    }

    fun loadTransactionById(id: String) {
        viewModelScope.launch {
            _detailState.value = TransactionDetailUiState.Loading
            val result = repository.getTransactionById(id)
            result.onSuccess { transaction ->
                _detailState.value = TransactionDetailUiState.Success(transaction)
            }.onFailure { error ->
                _detailState.value = TransactionDetailUiState.Error(error.message ?: "Gagal memuat detail transaksi")
            }
        }
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            _actionState.value = TransactionActionState.InProgress
            val result = repository.addTransaction(transaction)
            result.onSuccess {
                _actionState.value = TransactionActionState.Success
                loadTransactions()
            }.onFailure { error ->
                _actionState.value = TransactionActionState.Error(error.message ?: "Gagal menambah transaksi")
            }
        }
    }

    fun deleteTransaction(id: String) {
        viewModelScope.launch {
            _actionState.value = TransactionActionState.InProgress
            val result = repository.deleteTransaction(id)
            result.onSuccess {
                _actionState.value = TransactionActionState.Success
                loadTransactions()
            }.onFailure { error ->
                _actionState.value = TransactionActionState.Error(error.message ?: "Gagal menghapus transaksi")
            }
        }
    }

    fun resetActionState() {
        _actionState.value = TransactionActionState.Idle
    }
}
