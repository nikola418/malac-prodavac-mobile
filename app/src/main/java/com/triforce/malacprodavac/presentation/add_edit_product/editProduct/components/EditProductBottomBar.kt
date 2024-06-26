package com.triforce.malacprodavac.presentation.add_edit_product.editProduct.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import com.triforce.malacprodavac.BottomNavigationMenuContent
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.presentation.add_edit_product.components.AddEditSubmitButton
import com.triforce.malacprodavac.presentation.add_edit_product.editProduct.EditProductEvent
import com.triforce.malacprodavac.presentation.add_edit_product.editProduct.EditProductViewModel
import com.triforce.malacprodavac.presentation.components.BottomNavigationMenu

@Composable
fun EditProductBottomBar(
    navController: NavController,
    viewModel: EditProductViewModel,
    context: Context
) {
    Column {
        AddEditSubmitButton(
            Modifier.clickable {
                viewModel.onEvent(EditProductEvent.Submit(context))
            },
            isEdit = true
        )

        BottomNavigationMenu(
            navController = navController,
            items = listOf(
                BottomNavigationMenuContent(
                    title = "Početna",
                    graphicID = Icons.Default.Home,
                    screen = Screen.HomeScreen,
                    isActive = false
                ),
                BottomNavigationMenuContent(
                    title = "Market",
                    graphicID = ImageVector.vectorResource(R.drawable.logo_green),
                    screen = Screen.StoreScreen,
                    isActive = false
                ),
                BottomNavigationMenuContent(
                    title = "Profil",
                    graphicID = Icons.Default.Person,
                    screen = Screen.PrivateProfile,
                    isActive = false
                ),
                BottomNavigationMenuContent(
                    title = "Korpa",
                    graphicID = Icons.Default.ShoppingCart,
                    screen = Screen.CartScreen,
                    isActive = false
                )
            )
        )
    }
}