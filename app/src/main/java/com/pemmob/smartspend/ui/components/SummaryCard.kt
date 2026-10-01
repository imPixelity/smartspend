package com.pemmob.smartspend.ui.components

import android.content.res.Configuration
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
import com.pemmob.smartspend.ui.theme.ExpenseRed
import com.pemmob.smartspend.ui.theme.IncomeGreen
import com.pemmob.smartspend.ui.theme.SmartSpendTheme
import java.text.NumberFormat
import java.util.Locale

// Format uang Rupiah, mis. 15000.0 -> "Rp 15.000"
fun formatRupiah(amount: Double): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    formatter.maximumFractionDigits = 0
    return formatter.format(amount).replace("Rp", "Rp ").replace("  ", " ")
}

@Composable
fun SummaryCard(
    balance: Double,
    totalIncome: Double,
    totalExpense: Double,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total Saldo",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = formatRupiah(balance),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = "Pemasukan",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = formatRupiah(totalIncome),
                        style = MaterialTheme.typography.titleMedium,
                        color = IncomeGreen,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Pengeluaran",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = formatRupiah(totalExpense),
                        style = MaterialTheme.typography.titleMedium,
                        color = ExpenseRed,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SummaryCardPreview() {
    SmartSpendTheme {
        SummaryCard(
            balance = 1500000.0,
            totalIncome = 2000000.0,
            totalExpense = 500000.0,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SummaryCardDarkPreview() {
    SmartSpendTheme(darkTheme = true) {
        SummaryCard(
            balance = 1500000.0,
            totalIncome = 2000000.0,
            totalExpense = 500000.0,
            modifier = Modifier.padding(16.dp)
        )
    }
}
