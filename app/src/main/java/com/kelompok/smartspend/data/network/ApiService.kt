package com.kelompok.smartspend.data.network

import com.kelompok.smartspend.data.model.Transaction
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("transactions")
    suspend fun getTransactions(): List<Transaction>

    @GET("transactions/{id}")
    suspend fun getTransactionById(@Path("id") id: String): Transaction

    @POST("transactions")
    suspend fun addTransaction(@Body transaction: Transaction): Transaction

    @DELETE("transactions/{id}")
    suspend fun deleteTransaction(@Path("id") id: String): Response<Unit>
}
