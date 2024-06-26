package com.triforce.malacprodavac.presentation.cart.scheduling

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.triforce.malacprodavac.BottomNavigationMenuContent
import com.triforce.malacprodavac.LinearGradient
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.presentation.cart.CartDetails.components.GoBackNoSearch
import com.triforce.malacprodavac.presentation.cart.CartEvent
import com.triforce.malacprodavac.presentation.cart.CartViewModel
import com.triforce.malacprodavac.presentation.cart.components.ChooseDateAndTime
import com.triforce.malacprodavac.presentation.components.BottomNavigationMenu
import com.triforce.malacprodavac.presentation.components.RoundedBackgroundComp
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_GreenDark
import com.triforce.malacprodavac.ui.theme.MP_White

@Composable
fun ScheduleScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .background(MP_White)
            .fillMaxSize()
    ) {
        LinearGradient(color1 = MP_Green, color2 = MP_GreenDark)
        RoundedBackgroundComp(top = 65.dp, color = MP_White)
        GoBackNoSearch(msg = "Zakazivanje", navController = navController)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 20.dp, vertical = 200.dp)
                .align(Alignment.Center)
        ) {
            Text(
                text = "Zakazivanje Paketa",
                style = MaterialTheme.typography.h5,
                color = MP_Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            ChooseDateAndTime(viewModel)

            Button(
                onClick = {
                    viewModel.onEvent(CartEvent.makeOrder)
                    navController.navigate(Screen.DetailsOrderScreen.route)
                },
                colors = ButtonDefaults.buttonColors(MP_Green)
            ) {
                Text(
                    text = "Zakaži porudžbinu",
                    color = MP_White,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        BottomNavigationMenu(
            navController = navController,
            items = listOf(
                BottomNavigationMenuContent(
                    title = "Početna",
                    graphicID = Icons.Default.Home,
                    screen = Screen.HomeScreen,
                    isActive = false
                ),
                BottomNavigationMenuContent(
                    title = "Market",
                    graphicID = ImageVector.vectorResource(R.drawable.logo_green),
                    screen = Screen.StoreScreen,
                    isActive = false
                ),
                BottomNavigationMenuContent(
                    title = "Profil",
                    graphicID = Icons.Default.Person,
                    screen = Screen.PrivateProfile,
                    isActive = false
                ),
                BottomNavigationMenuContent(
                    title = "Korpa",
                    graphicID = Icons.Default.ShoppingCart,
                    screen = Screen.CartScreen,
                    isActive = true
                )
            ), modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}