package com.triforce.malacprodavac.presentation.myProducts.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.domain.model.products.Product
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_Gray
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_GreenDark
import com.triforce.malacprodavac.ui.theme.MP_GreenLight
import com.triforce.malacprodavac.ui.theme.MP_Pink
import com.triforce.malacprodavac.ui.theme.MP_Pink_Dark
import com.triforce.malacprodavac.ui.theme.MP_White
import kotlinx.coroutines.Dispatchers

@Composable
fun MyProductsPreviewItem(
    product: Product?,
    navController: NavController
) {
    if (product != null) {
        var isToggleChecked by remember { mutableStateOf(product.available) }
        val showDialog = remember { mutableStateOf(false) }

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
                    navController.navigate(Screen.ProductScreen.route + "?productId=${product.id}")
                }
        ) {
            val imageUrl =
                if (product.productMedia != null) "http://softeng.pmf.kg.ac.rs:10010/products/${product.productMedia.productId}/medias/${product.productMedia.id}" else null
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
                        AsyncImage(
                            model = imageRequest,
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxSize()
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
                            text = if (product.title.length <= 16) product.title
                            else product.title.take(16) + "...",

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

                            Switch(
                                checked = isToggleChecked,
                                onCheckedChange = {
                                    if (!it) showDialog.value = true
                                    else isToggleChecked = it
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MP_GreenDark,
                                    checkedTrackColor = MP_GreenLight
                                )
                            )

                            if (showDialog.value) {
                                AlertDialog(
                                    containerColor = MP_White,
                                    onDismissRequest = {
                                        showDialog.value = false
                                    },
                                    title = {
                                        Text(
                                            text = "Dostupnost proizvoda",
                                            style = MaterialTheme.typography.h5,
                                            color = MP_Pink_Dark,
                                            fontWeight = FontWeight.W300
                                        )
                                    },
                                    text = {
                                        Text(
                                            text = "Jeste li apsolutno sigurni da želite da proizvod ${product.title} više ne bude dostupan?",
                                            style = MaterialTheme.typography.body1,
                                            color = MP_Black,
                                            fontWeight = FontWeight.W300
                                        )
                                    },
                                    confirmButton = {
                                        Button(
                                            onClick = {
                                                isToggleChecked = false
                                                showDialog.value = false
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = MP_Green
                                            )
                                        ) {
                                            Text(
                                                text = "Da",
                                                style = MaterialTheme.typography.body1,
                                                color = MP_White
                                            )
                                        }
                                    },
                                    dismissButton = {
                                        Button(
                                            onClick = {
                                                showDialog.value = false
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = MP_Pink
                                            )
                                        ) {
                                            Text(
                                                text = "Ne",
                                                style = MaterialTheme.typography.body1,
                                                color = MP_White
                                            )
                                        }
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