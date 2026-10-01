package com.pemmob.smartspend.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pemmob.smartspend.data.model.Transaction
import com.pemmob.smartspend.data.model.TransactionCategories
import com.pemmob.smartspend.data.model.TransactionType
import com.pemmob.smartspend.ui.theme.SmartSpendTheme
import com.pemmob.smartspend.ui.viewmodel.TransactionActionState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    actionState: TransactionActionState,
    onSubmit: (Transaction) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentDate = rememberSaveable {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    var title by rememberSaveable { mutableStateOf("") }
    var amountText by rememberSaveable { mutableStateOf("") }
    var selectedType by rememberSaveable { mutableStateOf(TransactionType.EXPENSE) }
    var selectedCategory by rememberSaveable { mutableStateOf(TransactionCategories.expense.first()) }
    var dateText by rememberSaveable { mutableStateOf(currentDate) }
    var isCategoryDropdownExpanded by rememberSaveable { mutableStateOf(false) }

    val categories = if (selectedType == TransactionType.INCOME) {
        TransactionCategories.income
    } else {
        TransactionCategories.expense
    }

    val amountValue = amountText.toDoubleOrNull()
    val isFormValid = title.isNotBlank() && amountValue != null && amountValue > 0.0 && dateText.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Transaksi") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Tipe Transaksi",
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FilterChip(
                    selected = selectedType == TransactionType.EXPENSE,
                    onClick = {
                        selectedType = TransactionType.EXPENSE
                        selectedCategory = TransactionCategories.expense.first()
                    },
                    label = { Text("Pengeluaran") },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = selectedType == TransactionType.INCOME,
                    onClick = {
                        selectedType = TransactionType.INCOME
                        selectedCategory = TransactionCategories.income.first()
                    },
                    label = { Text("Pemasukan") },
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Judul Transaksi") },
                placeholder = { Text("mis. Makan Siang, Gaji") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = amountText,
                onValueChange = { amountText = it.filter { char -> char.isDigit() || char == '.' } },
                label = { Text("Nominal (Rp)") },
                placeholder = { Text("mis. 25000") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Kategori") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Pilih Kategori"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isCategoryDropdownExpanded = true }
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { isCategoryDropdownExpanded = true }
                )
                DropdownMenu(
                    expanded = isCategoryDropdownExpanded,
                    onDismissRequest = { isCategoryDropdownExpanded = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = {
                                selectedCategory = category
                                isCategoryDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = dateText,
                onValueChange = { dateText = it },
                label = { Text("Tanggal (yyyy-MM-dd)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            if (actionState is TransactionActionState.Error) {
                Text(
                    text = actionState.message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (isFormValid && amountValue != null) {
                        onSubmit(
                            Transaction(
                                id = "",
                                title = title.trim(),
                                amount = amountValue,
                                type = selectedType,
                                category = selectedCategory,
                                date = dateText.trim()
                            )
                        )
                    }
                },
                enabled = isFormValid && actionState !is TransactionActionState.InProgress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                if (actionState is TransactionActionState.InProgress) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
                Text("Simpan Transaksi")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddTransactionScreenPreview() {
    SmartSpendTheme {
        AddTransactionScreen(
            actionState = TransactionActionState.Idle,
            onSubmit = {},
            onNavigateBack = {}
        )
    }
}
