package com.kelompok.smartspend.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.kelompok.smartspend.data.model.Transaction
import com.kelompok.smartspend.ui.screens.AddTransactionScreen
import com.kelompok.smartspend.ui.screens.DetailScreen
import com.kelompok.smartspend.ui.screens.HomeScreen
import com.kelompok.smartspend.ui.viewmodel.TransactionActionState
import com.kelompok.smartspend.ui.viewmodel.TransactionViewModel

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: TransactionViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val detailState by viewModel.detailState.collectAsStateWithLifecycle()
    val actionState by viewModel.actionState.collectAsStateWithLifecycle()

    LaunchedEffect(actionState) {
        if (actionState is TransactionActionState.Success) {
            navController.popBackStack()
            viewModel.resetActionState()
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Home,
        modifier = modifier
    ) {
        composable<Screen.Home> {
            HomeScreen(
                uiState = uiState,
                onAddClick = { navController.navigate(Screen.AddTransaction) },
                onTransactionClick = { id: String -> navController.navigate(Screen.Detail(id)) },
                onRetry = { viewModel.loadTransactions() }
            )
        }

        composable<Screen.AddTransaction> {
            AddTransactionScreen(
                actionState = actionState,
                onSubmit = { transaction: Transaction -> viewModel.addTransaction(transaction) },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable<Screen.Detail> { backStackEntry ->
            val route = backStackEntry.toRoute<Screen.Detail>()
            LaunchedEffect(route.transactionId) {
                viewModel.loadTransactionById(route.transactionId)
            }

            DetailScreen(
                detailState = detailState,
                onDeleteClick = { viewModel.deleteTransaction(route.transactionId) },
                onNavigateBack = { navController.popBackStack() },
                onRetry = { viewModel.loadTransactionById(route.transactionId) }
            )
        }
    }
}
