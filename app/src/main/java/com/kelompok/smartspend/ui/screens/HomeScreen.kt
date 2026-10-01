package com.kelompok.smartspend.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kelompok.smartspend.ui.components.SummaryCard
import com.kelompok.smartspend.ui.components.TransactionItem
import com.kelompok.smartspend.ui.viewmodel.TransactionUiState

// STUB - akan diganti oleh Anggota D
@Composable
fun HomeScreen(
    uiState: TransactionUiState,
    onAddClick: () -> Unit,
    onTransactionClick: (String) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is TransactionUiState.Loading -> {
                CircularProgressIndicator()
            }
            is TransactionUiState.Error -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = uiState.message)
                    Button(onClick = onRetry) {
                        Text(text = "Coba Lagi")
                    }
                }
            }
            is TransactionUiState.Success -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    SummaryCard(
                        balance = uiState.balance,
                        totalIncome = uiState.totalIncome,
                        totalExpense = uiState.totalExpense,
                        modifier = Modifier.padding(16.dp)
                    )
                    Button(
                        onClick = onAddClick,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text("Tambah Transaksi")
                    }
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(uiState.transactions, key = { it.id }) { transaction ->
                            TransactionItem(
                                transaction = transaction,
                                onClick = { onTransactionClick(transaction.id) },
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
