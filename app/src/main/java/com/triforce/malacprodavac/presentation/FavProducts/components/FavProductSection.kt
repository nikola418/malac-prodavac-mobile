package com.triforce.malacprodavac.presentation.FavProducts.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.triforce.malacprodavac.domain.model.customers.FavoriteProduct
import com.triforce.malacprodavac.presentation.FavProducts.FavoriteViewModel

@Composable
fun FavProductSection(
    favoriteProducts: List<FavoriteProduct>,
    viewModel: FavoriteViewModel,
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier
                .requiredHeight(530.dp)
        ) {

            items(favoriteProducts.size) {
                FavProductItem(
                    favoriteProduct = favoriteProducts[it],
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
    }
}
