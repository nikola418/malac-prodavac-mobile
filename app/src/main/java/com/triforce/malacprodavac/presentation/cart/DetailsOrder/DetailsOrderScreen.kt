package com.triforce.malacprodavac.presentation.cart.DetailsOrder

import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.navigation.NavController
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.data.repository.cart.CartRepository
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_White

@Composable
fun DetailsOrderScreen(
    navController: NavController
) {
    Scaffold(
        content = { padding ->
            DetailsOrderScreenContent()
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    CartRepository.clearCart()
                    navController.navigate(Screen.HomeScreen.route)
                },
                backgroundColor = MP_White,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Vrati se na naslovnu stranu",
                    color = MP_Black,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    )
}