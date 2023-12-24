package com.triforce.malacprodavac.presentation.add_edit_product.editProduct

import androidx.compose.foundation.clickable
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.presentation.add_edit_product.editProduct.components.EditProductBottomBar
import com.triforce.malacprodavac.presentation.add_edit_product.editProduct.components.EditProductScreenContent
import com.triforce.malacprodavac.presentation.store.components.GoBackComp

@Composable
fun EditProductScreen(
    navController: NavController,
    viewModel: EditProductViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            GoBackComp("Izmeni proizvod",
                navController = navController,
                modifier = Modifier.clickable { navController.navigate(Screen.PrivateProfile.route) },
                isLight = true
            )
        },
        content = { padding ->
            EditProductScreenContent(navController, viewModel, padding)
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