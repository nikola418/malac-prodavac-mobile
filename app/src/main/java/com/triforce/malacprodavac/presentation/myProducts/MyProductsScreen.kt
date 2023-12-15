package com.triforce.malacprodavac.presentation.myProducts

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.triforce.malacprodavac.presentation.myProducts.components.MyProductsBottomBar
import com.triforce.malacprodavac.presentation.myProducts.components.MyProductsContentScreen
import com.triforce.malacprodavac.presentation.store.components.GoBackComp

@Composable
fun MyProductsScreen(
    navController: NavController,
    viewModel: MyProductsViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            GoBackComp("Moji proizvodi", navController, true)
        },
        content = { padding ->
            MyProductsContentScreen(navController, viewModel, padding)
        },
        bottomBar = {
            MyProductsBottomBar(navController)
        }
    )
}