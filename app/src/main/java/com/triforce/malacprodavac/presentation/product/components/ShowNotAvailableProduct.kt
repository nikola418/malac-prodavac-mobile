package com.triforce.malacprodavac.presentation.product.components

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.triforce.malacprodavac.domain.model.products.Product
import com.triforce.malacprodavac.presentation.FavProducts.FavoriteEvent
import com.triforce.malacprodavac.presentation.FavProducts.FavoriteViewModel
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_Orange_Dark
import com.triforce.malacprodavac.ui.theme.MP_Pink
import com.triforce.malacprodavac.ui.theme.MP_White

@Composable
fun ShowNotAvailableProduct(
    product: Product,
    viewModelFavourite: FavoriteViewModel
) {
    val isFavorite = remember { mutableStateOf(product.isFavored ?: false) }

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

        Text(
            text = "Nije dostupan",
            style = MaterialTheme.typography.h5,
            color = MP_White,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(MP_Orange_Dark)
                .width(width = 250.dp)
                .padding(vertical = 10.dp)
        )
    }
}