package com.triforce.malacprodavac.presentation.myTransactions.mySales

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.triforce.malacprodavac.presentation.myProducts.components.MyProductsBottomBar
import com.triforce.malacprodavac.presentation.myTransactions.mySales.components.MySalesContentScreen
import com.triforce.malacprodavac.presentation.store.components.GoBackComp

@Composable
fun MySalesScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            GoBackComp("Moje prodaje", navController, true)
        },
        content = { padding ->
            MySalesContentScreen(navController, padding)
        },
        bottomBar = {
            MyProductsBottomBar(navController)
        }
    )
}