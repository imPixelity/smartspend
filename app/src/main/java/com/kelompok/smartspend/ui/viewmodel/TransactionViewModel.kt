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
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException

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
            result.fold(
                onSuccess = { list ->
                    val sortedList = list.sortedByDescending { it.date }

                    var totalIncome = 0.0
                    var totalExpense = 0.0
                    sortedList.forEach { item ->
                        if (item.type.equals(TransactionType.INCOME, ignoreCase = true)) {
                            totalIncome += item.amount
                        } else if (item.type.equals(TransactionType.EXPENSE, ignoreCase = true)) {
                            totalExpense += item.amount
                        }
                    }
                    val balance = totalIncome - totalExpense

                    _uiState.value = TransactionUiState.Success(
                        transactions = sortedList,
                        totalIncome = totalIncome,
                        totalExpense = totalExpense,
                        balance = balance
                    )
                },
                onFailure = { throwable ->
                    if (throwable is CancellationException) throw throwable
                    _uiState.value = TransactionUiState.Error(mapErrorMessage(throwable))
                }
            )
        }
    }

    fun loadTransactionById(id: String) {
        viewModelScope.launch {
            _detailState.value = TransactionDetailUiState.Loading
            val result = repository.getTransactionById(id)
            result.fold(
                onSuccess = { transaction ->
                    _detailState.value = TransactionDetailUiState.Success(transaction)
                },
                onFailure = { throwable ->
                    if (throwable is CancellationException) throw throwable
                    _detailState.value = TransactionDetailUiState.Error(mapErrorMessage(throwable))
                }
            )
        }
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            _actionState.value = TransactionActionState.InProgress
            val result = repository.addTransaction(transaction)
            result.fold(
                onSuccess = {
                    _actionState.value = TransactionActionState.Success
                    loadTransactions()
                },
                onFailure = { throwable ->
                    if (throwable is CancellationException) throw throwable
                    _actionState.value = TransactionActionState.Error(mapErrorMessage(throwable))
                }
            )
        }
    }

    fun deleteTransaction(id: String) {
        viewModelScope.launch {
            _actionState.value = TransactionActionState.InProgress
            val result = repository.deleteTransaction(id)
            result.fold(
                onSuccess = {
                    _actionState.value = TransactionActionState.Success
                    loadTransactions()
                },
                onFailure = { throwable ->
                    if (throwable is CancellationException) throw throwable
                    _actionState.value = TransactionActionState.Error(mapErrorMessage(throwable))
                }
            )
        }
    }

    fun resetActionState() {
        _actionState.value = TransactionActionState.Idle
    }

    private fun mapErrorMessage(throwable: Throwable): String {
        return when (throwable) {
            is SocketTimeoutException -> "Koneksi ke server tenggat waktu (timeout). Silakan coba lagi."
            is UnknownHostException, is IOException -> "Tidak ada koneksi internet. Silakan periksa jaringan Anda."
            is HttpException -> "Terjadi kesalahan pada server (${throwable.code()}). Silakan coba beberapa saat lagi."
            else -> throwable.localizedMessage ?: "Terjadi kesalahan yang tidak diketahui. Silakan coba lagi."
        }
    }
}
