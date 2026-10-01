package com.kelompok.smartspend.data.network

import com.kelompok.smartspend.data.model.Transaction
import retrofit2.Response

// STUB - akan diganti oleh Anggota A
object RetrofitInstance {
    val api: ApiService by lazy {
        object : ApiService {
            override suspend fun getTransactions(): List<Transaction> = emptyList()
            override suspend fun getTransactionById(id: String): Transaction = Transaction()
            override suspend fun addTransaction(transaction: Transaction): Transaction = transaction
            override suspend fun deleteTransaction(id: String): Response<Unit> = Response.success(Unit)
        }
    }
}
