package com.triforce.malacprodavac.presentation.myTransactions.myPurchases

import androidx.compose.foundation.clickable
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.presentation.myProducts.components.MyProductsBottomBar
import com.triforce.malacprodavac.presentation.myTransactions.myPurchases.components.MyPurchasesContentScreen
import com.triforce.malacprodavac.presentation.store.components.GoBackComp

@Composable
fun MyPurchasesScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            GoBackComp("Moje porudžbine",
                navController = navController,
                modifier = Modifier.clickable { navController.navigate(Screen.HomeScreen.route) },
                isLight = true
            )
        },
        content = { padding ->
            MyPurchasesContentScreen(navController, padding)
        },
        bottomBar = {
            MyProductsBottomBar(navController)
        }
    )
}