package com.triforce.malacprodavac.presentation.myTransactions.mySales

import androidx.compose.foundation.clickable
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.presentation.myProducts.components.MyProductsBottomBar
import com.triforce.malacprodavac.presentation.myTransactions.mySales.components.MySalesContentScreen
import com.triforce.malacprodavac.presentation.store.components.GoBackComp

@Composable
fun MySalesScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            GoBackComp("Moje prodaje",
                navController = navController,
                modifier = Modifier.clickable { navController.navigate(Screen.HomeScreen.route) },
                isLight = true
            )
        },
        content = { padding ->
            MySalesContentScreen(navController, padding)
        },
        bottomBar = {
            MyProductsBottomBar(navController)
        }
    )
}