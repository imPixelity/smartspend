package com.kelompok.smartspend.data.repository

import com.kelompok.smartspend.data.model.Transaction
import com.kelompok.smartspend.data.model.TransactionType
import com.kelompok.smartspend.data.network.ApiService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class TransactionRepositoryTest {

    @Test
    fun testGetTransactionsFromApi() = runBlocking {
        val repository = TransactionRepository()
        val result = repository.getTransactions()

        assertTrue("getTransactions should succeed", result.isSuccess)
        val transactions = result.getOrNull()
        assertNotNull(transactions)
        assertTrue("Transactions list should not be empty", transactions!!.isNotEmpty())
    }

    @Test
    fun testAddAndDeleteTransaction() = runBlocking {
        val repository = TransactionRepository()
        val newTx = Transaction(
            title = "UnitTest Tx",
            amount = 50000.0,
            type = TransactionType.EXPENSE,
            category = "Makanan",
            date = "2026-10-01"
        )

        val addResult = repository.addTransaction(newTx)
        assertTrue("addTransaction should succeed", addResult.isSuccess)
        val created = addResult.getOrNull()
        assertNotNull(created)
        assertTrue("Created transaction should have non-empty id", created!!.id.isNotEmpty())

        val getResult = repository.getTransactionById(created.id)
        assertTrue("getTransactionById should succeed", getResult.isSuccess)
        assertEquals("UnitTest Tx", getResult.getOrNull()?.title)

        val deleteResult = repository.deleteTransaction(created.id)
        assertTrue("deleteTransaction should succeed", deleteResult.isSuccess)
    }

    @Test
    fun testOfflineHandlingWithFakeApi() = runBlocking {
        val fakeFailingApi = object : ApiService {
            override suspend fun getTransactions(): List<Transaction> {
                throw IOException("No internet connection")
            }

            override suspend fun getTransactionById(id: String): Transaction {
                throw IOException("No internet connection")
            }

            override suspend fun addTransaction(transaction: Transaction): Transaction {
                throw IOException("No internet connection")
            }

            override suspend fun deleteTransaction(id: String): Response<Unit> {
                throw IOException("No internet connection")
            }
        }

        val repository = TransactionRepository(api = fakeFailingApi)
        val result = repository.getTransactions()

        assertTrue("Should return failure Result on exception", result.isFailure)
        assertTrue(result.exceptionOrNull() is IOException)
    }
}
