package com.pemmob.smartspend.data.repository

import com.pemmob.smartspend.data.model.Transaction
import com.pemmob.smartspend.data.model.TransactionType

// STUB - akan diganti oleh Anggota A
open class TransactionRepository {
    private val dummyTransactions = mutableListOf(
        Transaction(
            id = "1",
            title = "Gaji Bulanan",
            amount = 5000000.0,
            type = TransactionType.INCOME,
            category = "Gaji",
            date = "2026-10-01"
        ),
        Transaction(
            id = "2",
            title = "Makan Siang",
            amount = 50000.0,
            type = TransactionType.EXPENSE,
            category = "Makanan",
            date = "2026-10-02"
        ),
        Transaction(
            id = "3",
            title = "Belanja Bulanan",
            amount = 450000.0,
            type = TransactionType.EXPENSE,
            category = "Belanja",
            date = "2026-10-03"
        )
    )

    open suspend fun getTransactions(): Result<List<Transaction>> {
        return Result.success(dummyTransactions.toList())
    }

    open suspend fun getTransactionById(id: String): Result<Transaction> {
        val transaction = dummyTransactions.find { it.id == id }
        return if (transaction != null) {
            Result.success(transaction)
        } else {
            Result.failure(NoSuchElementException("Transaksi dengan ID $id tidak ditemukan"))
        }
    }

    open suspend fun addTransaction(transaction: Transaction): Result<Transaction> {
        val newId = (dummyTransactions.size + 1).toString()
        val created = transaction.copy(id = newId)
        dummyTransactions.add(created)
        return Result.success(created)
    }

    open suspend fun deleteTransaction(id: String): Result<Unit> {
        val removed = dummyTransactions.removeIf { it.id == id }
        return if (removed) {
            Result.success(Unit)
        } else {
            Result.failure(NoSuchElementException("Transaksi dengan ID $id tidak ditemukan"))
        }
    }
}
