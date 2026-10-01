package com.kelompok.smartspend.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kelompok.smartspend.data.model.Transaction

// STUB - akan diganti oleh Anggota B
@Composable
fun TransactionItem(
    transaction: Transaction,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onClick() }
    ) {
        Text(text = "${transaction.title} - ${formatRupiah(transaction.amount)}")
    }
}
