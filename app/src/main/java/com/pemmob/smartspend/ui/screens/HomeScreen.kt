package com.pemmob.smartspend.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pemmob.smartspend.data.model.Transaction
import com.pemmob.smartspend.data.model.TransactionType
import com.pemmob.smartspend.ui.components.SummaryCard
import com.pemmob.smartspend.ui.components.TransactionItem
import com.pemmob.smartspend.ui.theme.SmartSpendTheme
import com.pemmob.smartspend.ui.viewmodel.TransactionUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: TransactionUiState,
    onAddClick: () -> Unit,
    onTransactionClick: (String) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by rememberSaveable { mutableStateOf("ALL") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SmartSpend") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Transaksi"
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is TransactionUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is TransactionUiState.Error -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = uiState.message,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onRetry) {
                            Text("Coba Lagi")
                        }
                    }
                }

                is TransactionUiState.Success -> {
                    val filteredTransactions = when (selectedFilter) {
                        TransactionType.INCOME -> uiState.transactions.filter { it.type == TransactionType.INCOME }
                        TransactionType.EXPENSE -> uiState.transactions.filter { it.type == TransactionType.EXPENSE }
                        else -> uiState.transactions
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            SummaryCard(
                                balance = uiState.balance,
                                totalIncome = uiState.totalIncome,
                                totalExpense = uiState.totalExpense,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }

                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                FilterChip(
                                    selected = selectedFilter == "ALL",
                                    onClick = { selectedFilter = "ALL" },
                                    label = { Text("Semua") }
                                )
                                FilterChip(
                                    selected = selectedFilter == TransactionType.INCOME,
                                    onClick = { selectedFilter = TransactionType.INCOME },
                                    label = { Text("Pemasukan") }
                                )
                                FilterChip(
                                    selected = selectedFilter == TransactionType.EXPENSE,
                                    onClick = { selectedFilter = TransactionType.EXPENSE },
                                    label = { Text("Pengeluaran") }
                                )
                            }
                        }

                        if (filteredTransactions.isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(32.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Belum ada transaksi",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        } else {
                            items(
                                items = filteredTransactions,
                                key = { it.id }
                            ) { transaction ->
                                TransactionItem(
                                    transaction = transaction,
                                    onClick = { onTransactionClick(transaction.id) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)
                                )
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(80.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SmartSpendTheme {
        HomeScreen(
            uiState = TransactionUiState.Success(
                transactions = listOf(
                    Transaction("1", "Gaji Bulan Ini", 5000000.0, TransactionType.INCOME, "Gaji", "2026-10-01"),
                    Transaction("2", "Makan Siang", 25000.0, TransactionType.EXPENSE, "Makanan", "2026-10-02")
                ),
                totalIncome = 5000000.0,
                totalExpense = 25000.0,
                balance = 4975000.0
            ),
            onAddClick = {},
            onTransactionClick = {},
            onRetry = {}
        )
    }
}
