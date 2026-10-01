package com.kelompok.smartspend.ui.navigation

import kotlinx.serialization.Serializable

// STUB - akan diganti oleh Anggota D
@Serializable
sealed interface Screen {
    @Serializable
    data object Home : Screen

    @Serializable
    data object AddTransaction : Screen

    @Serializable
    data class Detail(val transactionId: String) : Screen
}
