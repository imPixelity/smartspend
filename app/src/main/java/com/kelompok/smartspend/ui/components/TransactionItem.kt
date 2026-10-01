package com.kelompok.smartspend.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok.smartspend.data.model.Transaction
import com.kelompok.smartspend.data.model.TransactionType
import com.kelompok.smartspend.ui.theme.ExpenseRed
import com.kelompok.smartspend.ui.theme.IncomeGreen
import com.kelompok.smartspend.ui.theme.SmartSpendTheme

@Composable
fun TransactionItem(
    transaction: Transaction,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        text = transaction.category,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = " • ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = transaction.date,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            val isIncome = transaction.type == TransactionType.INCOME
            val prefix = if (isIncome) "+" else "-"
            val color = if (isIncome) IncomeGreen else ExpenseRed

            Text(
                text = "$prefix${formatRupiah(transaction.amount)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionItemPreviewIncome() {
    SmartSpendTheme {
        TransactionItem(
            transaction = Transaction(
                id = "1",
                title = "Gaji Bulanan",
                amount = 5000000.0,
                type = TransactionType.INCOME,
                category = "Gaji",
                date = "2026-10-01"
            ),
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TransactionItemPreviewExpense() {
    SmartSpendTheme(darkTheme = true) {
        TransactionItem(
            transaction = Transaction(
                id = "2",
                title = "Makan Siang",
                amount = 25000.0,
                type = TransactionType.EXPENSE,
                category = "Makanan",
                date = "2026-10-02"
            ),
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
