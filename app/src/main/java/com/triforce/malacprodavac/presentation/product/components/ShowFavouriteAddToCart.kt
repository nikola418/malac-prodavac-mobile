package com.triforce.malacprodavac.presentation.product.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.domain.model.products.Product
import com.triforce.malacprodavac.presentation.FavProducts.FavoriteEvent
import com.triforce.malacprodavac.presentation.FavProducts.FavoriteViewModel
import com.triforce.malacprodavac.presentation.cart.CartViewModel
import com.triforce.malacprodavac.presentation.product.ProductEvent
import com.triforce.malacprodavac.presentation.product.ProductViewModel
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_Pink
import com.triforce.malacprodavac.ui.theme.MP_White

@Composable
fun ShowFavouriteAddToCart(
    product: Product,
    navController: NavController,
    viewModel: ProductViewModel,
    viewModelFavourite: FavoriteViewModel
) {
    val isFavorite = remember { mutableStateOf(product.isFavored ?: false) }

    val cartViewModel: CartViewModel = hiltViewModel()

    val context = LocalContext.current

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 6.dp, start = 5.dp, end = 5.dp
            )
            .shadow(
                elevation = 5.dp, spotColor = MP_Black, shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(MP_White)
            .padding(
                vertical = 10.dp,
            )
    ) {
        Icon(
            imageVector = if (isFavorite.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = "favourite_product",
            tint = MP_Pink,
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    if (!isFavorite.value) {
                        viewModelFavourite.onEvent(
                            FavoriteEvent.AddFavProduct(
                                product.id
                            )
                        )
                    } else {
                        viewModelFavourite.onEvent(
                            FavoriteEvent.DeleteFavProduct(
                                product.id
                            )
                        )
                    }
                    isFavorite.value = !isFavorite.value
                }
        )

        Text(text = "Dodaj u korpu",
            style = MaterialTheme.typography.h5,
            color = MP_White,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clickable {
                    if (!viewModel.state.isBuyed) {
                        viewModel.onEvent(ProductEvent.buyProduct)
                        Toast
                            .makeText(
                                context, "Proizvod je dodat u korpu", Toast.LENGTH_LONG
                            )
                            .show()

                        cartViewModel.addToCart(product, viewModel.state.shop)
                        navController.navigate(Screen.CartScreen.route)

                    } else {
                        Toast
                            .makeText(
                                context, "Proizvod se već nalazi u korpi", Toast.LENGTH_LONG
                            )
                            .show()
                    }
                }
                .clip(RoundedCornerShape(20.dp))
                .background(MP_Pink)
                .width(width = 250.dp)
                .padding(vertical = 10.dp))
    }
}