package com.triforce.malacprodavac.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.triforce.malacprodavac.presentation.FavShops.FavoriteShopEvent
import com.triforce.malacprodavac.presentation.FavShops.FavoriteShopViewModel
import com.triforce.malacprodavac.presentation.profile.profilePublic.ProfilePublicViewModel
import com.triforce.malacprodavac.ui.theme.MP_Pink
import com.triforce.malacprodavac.ui.theme.MP_White

@Composable
fun CallToActionFavourite(
    profileViewModel: ProfilePublicViewModel,
    content: String,
    viewModelFavShop: FavoriteShopViewModel = hiltViewModel()
) {
    val shop = profileViewModel.state.shop

    val isFavorite = remember { mutableStateOf(shop?.isFavored ?: false) }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MP_Pink)
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = content,
            style = MaterialTheme.typography.subtitle1,
            color = MP_White,
            fontWeight = FontWeight.W500,
            modifier = Modifier
                .width(210.dp)
        )
        if (shop != null)
            Icon(
                imageVector = if (isFavorite.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = "favourite_shop",
                tint = MP_White,
                modifier = Modifier
                    .size(100.dp)
                    .clickable {
                        if (!isFavorite.value) {
                            viewModelFavShop.onEvent(
                                FavoriteShopEvent.AddFavShop(
                                    shop.id
                                )
                            )
                        } else {
                            if (shop.favoriteShops?.isNotEmpty() == true)
                                viewModelFavShop.onEvent(
                                    FavoriteShopEvent.DeleteFavShop(
                                        shop.favoriteShops[0].id
                                    )
                                )
                        }
                        isFavorite.value = !isFavorite.value
                    }
            )
    }
}