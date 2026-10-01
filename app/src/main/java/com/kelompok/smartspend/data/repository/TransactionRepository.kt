package com.kelompok.smartspend.data.repository

import com.kelompok.smartspend.data.model.Transaction
import com.kelompok.smartspend.data.model.TransactionType
import com.kelompok.smartspend.data.network.ApiService
import com.kelompok.smartspend.data.network.RetrofitInstance

// STUB - akan diganti oleh Anggota A
class TransactionRepository(
    private val api: ApiService = RetrofitInstance.api
) {
    private val dummyTransactions = mutableListOf(
        Transaction("1", "Gaji Bulan Ini", 5000000.0, TransactionType.INCOME, "Gaji", "2026-10-01"),
        Transaction("2", "Makan Siang", 25000.0, TransactionType.EXPENSE, "Makanan", "2026-10-02"),
        Transaction("3", "Bensin Motor", 30000.0, TransactionType.EXPENSE, "Transportasi", "2026-10-03")
    )

    suspend fun getTransactions(): Result<List<Transaction>> {
        return Result.success(dummyTransactions.toList())
    }

    suspend fun getTransactionById(id: String): Result<Transaction> {
        val item = dummyTransactions.find { it.id == id }
        return if (item != null) Result.success(item) else Result.failure(Exception("Transaction not found"))
    }

    suspend fun addTransaction(transaction: Transaction): Result<Transaction> {
        val newId = (dummyTransactions.size + 1).toString()
        val newItem = transaction.copy(id = newId)
        dummyTransactions.add(newItem)
        return Result.success(newItem)
    }

    suspend fun deleteTransaction(id: String): Result<Unit> {
        dummyTransactions.removeAll { it.id == id }
        return Result.success(Unit)
    }
}
