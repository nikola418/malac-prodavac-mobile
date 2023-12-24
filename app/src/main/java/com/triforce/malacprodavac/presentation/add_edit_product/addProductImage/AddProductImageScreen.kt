package com.triforce.malacprodavac.presentation.add_edit_product.addProductImage

import androidx.compose.foundation.clickable
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.presentation.add_edit_product.addProductImage.components.AddProductImageContentScreen
import com.triforce.malacprodavac.presentation.add_edit_product.editProduct.EditProductViewModel
import com.triforce.malacprodavac.presentation.add_edit_product.editProduct.components.EditProductBottomBar
import com.triforce.malacprodavac.presentation.store.components.GoBackComp

@Composable
fun AddProductImageScreen(
    navController: NavController,
    viewModel: EditProductViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            GoBackComp("Dodaj sliku",
                navController = navController,
                modifier = Modifier.clickable { navController.popBackStack() },
                isLight = true
            )
        },
        content = { padding ->
            AddProductImageContentScreen(navController, viewModel, padding)
        },
        bottomBar = {
            EditProductBottomBar(
                navController = navController,
                viewModel = viewModel,
                context = context
            )
        }
    )
}