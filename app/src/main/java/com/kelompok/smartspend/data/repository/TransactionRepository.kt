package com.kelompok.smartspend.data.repository

import com.kelompok.smartspend.data.model.Transaction
import com.kelompok.smartspend.data.network.ApiService
import com.kelompok.smartspend.data.network.RetrofitInstance
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException

class TransactionRepository(
    private val api: ApiService = RetrofitInstance.api
) {
    suspend fun getTransactions(): Result<List<Transaction>> {
        return try {
            val transactions = api.getTransactions()
            Result.success(transactions)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    suspend fun getTransactionById(id: String): Result<Transaction> {
        return try {
            val transaction = api.getTransactionById(id)
            Result.success(transaction)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    suspend fun addTransaction(transaction: Transaction): Result<Transaction> {
        return try {
            val createdTransaction = api.addTransaction(transaction)
            Result.success(createdTransaction)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    suspend fun deleteTransaction(id: String): Result<Unit> {
        return try {
            val response = api.deleteTransaction(id)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}
