package com.triforce.malacprodavac.presentation.cart.components

import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.presentation.cart.CartViewModel
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_White

@Composable
fun CartScreenContent(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {
    val cartItems by viewModel.cartItems.collectAsState()
    val totalPrice = String.format("%.2f", viewModel.cartState.totalPrice)
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(MP_White)
            .fillMaxSize()
    ) {

        DisplayCartItems(cartItems = cartItems, navController = navController)
        Spacer(modifier = Modifier.padding(6.dp))

        Text(
            text = "Ukupno: ${totalPrice} rsd",
            style = MaterialTheme.typography.h6,
            color = MP_Green
        )
        Spacer(modifier = Modifier.padding(6.dp))

        Button(
            onClick = {
                if (cartItems.isNotEmpty()) navController.navigate(Screen.CartDetailsScreen.route)
                else {
                    Toast
                        .makeText(
                            context,
                            "Korpa je prazna!",
                            Toast.LENGTH_LONG
                        )
                        .show()
                }
            },
            colors = ButtonDefaults.buttonColors(MP_Green)
        ) {
            Text(
                text = "Nastavi na plaćanje",
                color = MP_White,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 50.dp, vertical = 6.dp)
            )
        }
    }
}