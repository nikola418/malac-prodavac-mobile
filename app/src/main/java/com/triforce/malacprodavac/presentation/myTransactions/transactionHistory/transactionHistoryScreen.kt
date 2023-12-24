package com.triforce.malacprodavac.presentation.myTransactions.transactionHistory

import androidx.compose.foundation.clickable
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.presentation.myProducts.components.MyProductsBottomBar
import com.triforce.malacprodavac.presentation.myTransactions.transactionHistory.components.TransactionHistoryContentScreen
import com.triforce.malacprodavac.presentation.store.components.GoBackComp

@Composable
fun TransactionHistoryScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            GoBackComp("Transakcije",
                navController = navController,
                modifier = Modifier.clickable { navController.navigate(Screen.HomeScreen.route) },
                isLight = true
            )
        },
        content = { padding ->
            TransactionHistoryContentScreen(navController, padding)
        },
        bottomBar = {
            MyProductsBottomBar(navController)
        }
    )
}