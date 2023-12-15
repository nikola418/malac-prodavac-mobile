package com.triforce.malacprodavac.presentation.myProducts.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.triforce.malacprodavac.domain.model.products.Product

@Composable
fun MyProductsPreview(
    products: List<Product>?,
    navController: NavController
) {
    if (products != null)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 7.5.dp, vertical = 1.5.dp)
        ) {
            items(products.size) { id ->
                MyProductsPreviewItem(product = products[id], navController = navController)
            }
        }
}