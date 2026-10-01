package com.kelompok.smartspend.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kelompok.smartspend.data.model.Transaction
import com.kelompok.smartspend.ui.viewmodel.TransactionActionState

// STUB - akan diganti oleh Anggota D
@Composable
fun AddTransactionScreen(
    actionState: TransactionActionState,
    onSubmit: (Transaction) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Form Tambah Transaksi (STUB)")
        Button(onClick = onNavigateBack) {
            Text(text = "Kembali")
        }
    }
}
