package com.triforce.malacprodavac.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.domain.model.products.Product
import com.triforce.malacprodavac.presentation.FavProducts.FavoriteEvent
import com.triforce.malacprodavac.presentation.FavProducts.FavoriteViewModel
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_Gray
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_Pink
import com.triforce.malacprodavac.ui.theme.MP_White
import kotlinx.coroutines.Dispatchers

@Composable
fun ShowHighlightSectionComp(
    navController: NavController,
    products: List<Product>?,
    title: String,
    route: String
) {
    val subProducts = if (products != null) {
        products.subList(
            0, if (products.size > 3) {
                4
            } else {
                products.size
            }
        )
    } else null

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.body2,
                color = MP_Black,
                fontWeight = FontWeight.W500
            )
            Text(
                text = "Vidi više",
                style = MaterialTheme.typography.caption,
                color = MP_White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        navController.navigate(route)
                    }
                    .clip(RoundedCornerShape(15.dp))
                    .background(MP_Pink)
                    .padding(vertical = 6.dp, horizontal = 15.dp)
            )

        }

        ShowHighlightedProducts(products = subProducts, navController = navController)
    }
}

@Composable
fun ShowHighlightedProducts(
    isLoading: Boolean = false,
    loadNextPage: () -> Unit = {},
    isLastPage: Boolean = true,
    products: List<Product>?,
    navController: NavController,
    bottomNavigation: Boolean = false
) {

    val scrollState = rememberLazyListState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            top = if (bottomNavigation) 1.5.dp else 16.dp,
            start = 10.dp,
            end = 10.dp,
            bottom = if (bottomNavigation) 80.dp else 15.dp
        )
    ) {
        if (products != null) {
            items(products) {
                HighlightSectionProduct(
                    product = it,
                    navController = navController
                )
            }
            item {
                if (isLoading) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp), horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    LaunchedEffect(scrollState.canScrollForward) {
                        if (!isLastPage && !scrollState.canScrollForward) {
                            loadNextPage()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HighlightSectionProduct(
    product: Product?,
    navController: NavController,
    viewModelFavourite: FavoriteViewModel = hiltViewModel(),
) {
    val isFavorite = remember { mutableStateOf(product?.isFavored ?: false) }

    BoxWithConstraints(
        modifier = Modifier
            .padding(start = 7.5.dp, end = 7.5.dp, bottom = 15.dp)
            .shadow(
                elevation = 5.dp,
                spotColor = MP_Black,
                shape = RoundedCornerShape(7.5.dp)
            )
            .aspectRatio(0.8F)
            .clip(RoundedCornerShape(10.dp))
            .background(MP_White)
            .clickable {
                if (product != null) {
                    navController.navigate(Screen.ProductScreen.route + "?productId=${product.id}")
                }
            }
    ) {
        if (product != null) {

            val imageUrl =
                if (product.productMedia != null) "http://softeng.pmf.kg.ac.rs:10010/products/${product.productMedia.productId}/medias/${product.productMedia.id}" else null

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Box(
                        modifier = Modifier
                            .size(
                                width = 150.dp,
                                height = 125.dp
                            )
                            .background(MP_Gray)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        val placeholder = R.drawable.logo_green
                        val imageRequest = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .dispatcher(Dispatchers.IO)
                            .memoryCachePolicy(CachePolicy.ENABLED)
                            .memoryCacheKey(imageUrl)
                            .placeholder(placeholder)
                            .error(placeholder)
                            .fallback(placeholder)
                            .build()
                        AsyncImage(
                            model = imageRequest,
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .padding(
                                top = 7.5.dp
                            )
                    ) {
                        Text(
                            text = if (product.title.length <= 16) {
                                product.title
                            } else {
                                product.title.take(16) + "..."
                            },
                            style = MaterialTheme.typography.body2,
                            color = MP_Black
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = 5.dp
                                )
                        ) {
                            Text(
                                text = product.price.toString() + " rsd",
                                style = MaterialTheme.typography.body2,
                                color = MP_Green,
                                fontWeight = FontWeight.Bold
                            )
                            if (product.isFavored != null) {
                                Icon(
                                    imageVector = if (isFavorite.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                    contentDescription = "favourite_product",
                                    tint = MP_Pink,
                                    modifier = Modifier
                                        .size(20.dp)
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
                            }
                        }
                    }
                }
            }
        }
    }
}