package com.triforce.malacprodavac.presentation.cart.DetailsOrder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.data.repository.cart.CartRepository
import com.triforce.malacprodavac.presentation.cart.CartViewModel
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_Pink_Dark
import com.triforce.malacprodavac.ui.theme.MP_White
import com.triforce.malacprodavac.util.enum.DeliveryMethod
import kotlinx.coroutines.Dispatchers

@Composable
fun DetailsOrderScreenContent(
    viewModel: CartViewModel = hiltViewModel()
) {
    val cartState = viewModel.cartState
    val cartItems = viewModel.cartItems

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MP_Green)
            .padding(horizontal = 20.dp)
            .padding(top = 40.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .shadow(
                    elevation = 5.dp,
                    spotColor = MP_Black,
                    shape = RoundedCornerShape(7.5.dp)
                )
                .clip(RoundedCornerShape(10.dp))
                .background(MP_White)
                .padding(horizontal = 20.dp)
                .height(550.dp)
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(6.dp))

                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "CheckCircle",
                    tint = MP_Green,
                    modifier = Modifier
                        .size(90.dp)
                )

                Spacer(modifier = Modifier.padding(6.dp))

                Text(
                    text = "Kod za praćenje:",
                    style = MaterialTheme.typography.body1,
                    color = MP_Black
                )

                Text(
                    text = generateRandomString(7),
                    style = MaterialTheme.typography.h6,
                    color = MP_Pink_Dark,
                    fontWeight = FontWeight.W400
                )

                Spacer(modifier = Modifier.padding(6.dp))

                Text(
                    text = "Datum potvrde",
                    style = MaterialTheme.typography.body1,
                    color = MP_Black
                )

                Text(
                    text = cartState.localDate,
                    style = MaterialTheme.typography.h6,
                    color = MP_Pink_Dark,
                    fontWeight = FontWeight.W400
                )

                Spacer(modifier = Modifier.padding(6.dp))
            }

            Text(
                text = "Način plaćanja:\n${CartRepository.getPayment()}",
                style = MaterialTheme.typography.body1,
                color = MP_Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(6.dp))

            Text(
                text = "Podaci za slanje:\n${cartState.user?.address}\n${cartState.user?.phoneNumber}",
                style = MaterialTheme.typography.body1,
                color = MP_Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(6.dp))

            if (CartRepository.getShipping() == DeliveryMethod.SelfPickup) {
                Text(
                    text = "Preuzimanja paketa:\n${CartRepository.getScheduleDate()} ${CartRepository.getScheduleTime()}",
                    style = MaterialTheme.typography.body1,
                    color = MP_Black,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.padding(6.dp))
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                for (cartItem in cartItems.collectAsState().value) {
                    Row(modifier = Modifier.padding(bottom = 6.dp)) {
                        val imageUrl =
                            if (cartItem.product.productMedia != null) "http://softeng.pmf.kg.ac.rs:10010/products/${cartItem.product.productMedia.productId}/medias/${cartItem.product.productMedia.id}" else null
                        Box(
                            modifier = Modifier
                                .size(
                                    width = 21.dp,
                                    height = 21.dp
                                )
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
                        val productPrice = String.format("%.2f", cartItem.product.price)

                        Text(
                            text = "${cartItem.product.title}, ${cartItem.quantity.value}x $productPrice rsd",
                            style = MaterialTheme.typography.body1,
                            color = MP_Black,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                    }
                }
            }

            val totalPrice = String.format("%.2f", viewModel.cartState.totalPrice)
            Spacer(modifier = Modifier.padding(6.dp))

            Text(
                text = "Ukupan iznos:\n${totalPrice} rsd",
                style = MaterialTheme.typography.h6,
                color = MP_Pink_Dark,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center
            )
        }
    }
}

fun generateRandomString(length: Int): String {
    val charset = ('A'..'Z') + ('0'..'9')
    return List(length) { charset.random() }.joinToString("")
}