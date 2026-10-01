package com.kelompok.smartspend.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kelompok.smartspend.ui.viewmodel.TransactionDetailUiState

// STUB - akan diganti oleh Anggota D
@Composable
fun DetailScreen(
    detailState: TransactionDetailUiState,
    onDeleteClick: () -> Unit,
    onNavigateBack: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (detailState) {
            is TransactionDetailUiState.Loading -> {
                CircularProgressIndicator()
            }
            is TransactionDetailUiState.Error -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = detailState.message)
                    Button(onClick = onRetry) {
                        Text(text = "Coba Lagi")
                    }
                }
            }
            is TransactionDetailUiState.Success -> {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Detail Transaksi: ${detailState.transaction.title}")
                    Button(onClick = onDeleteClick) {
                        Text(text = "Hapus")
                    }
                    Button(onClick = onNavigateBack) {
                        Text(text = "Kembali")
                    }
                }
            }
        }
    }
}
