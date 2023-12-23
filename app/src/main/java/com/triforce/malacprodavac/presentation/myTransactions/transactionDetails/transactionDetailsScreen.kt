package com.triforce.malacprodavac.presentation.myTransactions.transactionDetails

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.triforce.malacprodavac.presentation.myProducts.components.MyProductsBottomBar
import com.triforce.malacprodavac.presentation.myTransactions.transactionDetails.components.TransactionDetailsContentScreen
import com.triforce.malacprodavac.presentation.store.components.GoBackComp

@Composable
fun TransactionDetailsScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            GoBackComp("Detalji", navController, true)
        },
        content = { padding ->
            TransactionDetailsContentScreen(padding)
        },
        bottomBar = {
            MyProductsBottomBar(navController)
        }
    )
}