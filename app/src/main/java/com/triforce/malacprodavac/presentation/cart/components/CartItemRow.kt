package com.triforce.malacprodavac.presentation.cart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.presentation.cart.CartEvent
import com.triforce.malacprodavac.presentation.cart.CartViewModel
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_Gray
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_Orange_Dark
import com.triforce.malacprodavac.ui.theme.MP_Pink

@Composable
fun CartItemRow(
    cartItem: CartItem,
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {

    BoxWithConstraints(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .shadow(
                elevation = 5.dp,
                spotColor = MP_Black,
                shape = RoundedCornerShape(3.5.dp)
            )
            .clip(RoundedCornerShape(5.dp))
            .background(MP_Gray)
            .requiredHeight(120.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth().align(Alignment.TopStart),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = cartItem.product.title,
                    fontWeight = FontWeight.W500,
                    style = MaterialTheme.typography.h6,
                    color = MP_Black
                )

                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "DeleteCartItem",
                    tint = MP_Pink,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            viewModel.removeFromCart(cartItem)
                        }
                )
            }

            Text(
                text = "${cartItem.shop?.businessName}",
                fontWeight = FontWeight.W400,
                style = MaterialTheme.typography.body1,
                color = MP_Pink,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable {
                        navController.navigate(Screen.PublicProfile.route + "?id=${cartItem.shop!!.id}&role=1")
                    }
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .align(Alignment.BottomStart)
            ) {
                Text(
                    text = cartItem.quantity.value.toString() + "X ",
                    style = MaterialTheme.typography.body1,
                    color = MP_Black,
                )
                Text(
                    text = cartItem.price.value.toString() + " rsd",
                    style = MaterialTheme.typography.body1,
                    color = MP_Green,
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .requiredWidth(130.dp)
                    .requiredHeight(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "AddOne",
                    tint = MP_Green,
                    modifier = Modifier
                        .size(35.dp)
                        .align(Alignment.BottomCenter)
                        .clickable {
                            cartItem.quantity.value++
                            viewModel.onEvent(CartEvent.quantityChange)
                        }
                )

                Icon(
                    painter = painterResource(id = R.drawable.round_remove_circle_24),
                    contentDescription = "RemoveOne",
                    tint = MP_Orange_Dark,
                    modifier = Modifier
                        .size(35.dp)
                        .align(Alignment.BottomEnd)
                        .clickable {
                            if (cartItem.quantity.value > 1) {
                                cartItem.quantity.value--
                                viewModel.onEvent(CartEvent.quantityChange)
                            }
                        }
                )
            }
        }
    }
}
