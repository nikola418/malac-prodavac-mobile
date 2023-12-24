package com.triforce.malacprodavac.presentation.product

import androidx.compose.foundation.clickable
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.presentation.FavProducts.FavoriteViewModel
import com.triforce.malacprodavac.presentation.product.components.ProductBottomBar
import com.triforce.malacprodavac.presentation.product.components.ProductScreenContent
import com.triforce.malacprodavac.presentation.store.components.GoBackComp

@Composable
fun ProductScreen(
    navController: NavController,
    viewModel: ProductViewModel = hiltViewModel(),
    viewModelFavProduct: FavoriteViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            GoBackComp(
                "Malac pijaca",
                navController = navController,
                modifier = Modifier.clickable { navController.navigate(Screen.StoreScreen.route) },
                isLight = true
            )
        },
        content = { padding ->
            ProductScreenContent(navController, viewModel, padding)
        },
        bottomBar = {
            ProductBottomBar(navController, viewModel, viewModelFavProduct)
        }
    )
}