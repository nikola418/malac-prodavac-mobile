package com.triforce.malacprodavac.presentation.cart.CartDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.data.repository.cart.CartRepository
import com.triforce.malacprodavac.presentation.cart.CartEvent
import com.triforce.malacprodavac.presentation.cart.CartViewModel
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_White
import com.triforce.malacprodavac.util.enum.DeliveryMethod

@Composable
fun CartDetailsScreenContent(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {
    val totalPrice = String.format("%.2f", viewModel.cartState.totalPrice)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(MP_White)
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = "Plaćanje",
            style = MaterialTheme.typography.h6,
            color = MP_Black
        )
        Spacer(modifier = Modifier.padding(12.dp))

        AddressType(viewModel)
        Spacer(modifier = Modifier.padding(6.dp))

        PaymentType(viewModel)
        Spacer(modifier = Modifier.padding(6.dp))

        ShippingType(viewModel)
        Spacer(modifier = Modifier.padding(6.dp))

        Text(
            text = "Ukupno: ${totalPrice} rsd",
            style = MaterialTheme.typography.body1,
            color = MP_Black
        )
        Spacer(modifier = Modifier.padding(6.dp))

        Button(
            onClick = {
                if (CartRepository.getShipping() == DeliveryMethod.ByCourier) {
                    viewModel.onEvent(CartEvent.makeOrder)
                    navController.navigate(Screen.DetailsOrderScreen.route)
                } else
                    navController.navigate(Screen.SchedulingScreen.route)
            },
            colors = ButtonDefaults.buttonColors(MP_Green)
        ) {
            Text(
                text = "Izvrši porudžbinu",
                color = MP_White,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 50.dp, vertical = 6.dp)
            )
        }
    }
}