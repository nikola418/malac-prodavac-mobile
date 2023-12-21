package com.triforce.malacprodavac.presentation.myTransactions.myPurchases

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.triforce.malacprodavac.presentation.myProducts.components.MyProductsBottomBar
import com.triforce.malacprodavac.presentation.myTransactions.myPurchases.components.MyPurchasesContentScreen
import com.triforce.malacprodavac.presentation.store.components.GoBackComp

@Composable
fun MyPurchasesScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            GoBackComp("Moje Kupovine", navController, true)
        },
        content = { padding ->
            MyPurchasesContentScreen(navController, padding)
        },
        bottomBar = {
            MyProductsBottomBar(navController)
        }
    )
}