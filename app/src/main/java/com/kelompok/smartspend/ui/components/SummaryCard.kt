package com.kelompok.smartspend.ui.components

import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.text.NumberFormat
import java.util.Locale

// STUB - akan diganti oleh Anggota B
@Composable
fun SummaryCard(
    balance: Double,
    totalIncome: Double,
    totalExpense: Double,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Text(text = "SummaryCard Stub - Balance: ${formatRupiah(balance)}")
    }
}

// Format uang Rupiah, mis. 15000.0 -> "Rp 15.000"
fun formatRupiah(amount: Double): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    return formatter.format(amount)
}
