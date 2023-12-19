package com.triforce.malacprodavac.presentation.profile.profilePrivate.userScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.triforce.malacprodavac.BottomNavigationMenuContent
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.presentation.components.BottomNavigationMenu
import com.triforce.malacprodavac.presentation.components.ShowHighlightSectionComp
import com.triforce.malacprodavac.presentation.profile.components.ProfilePrivateHeroComp
import com.triforce.malacprodavac.presentation.profile.components.ShopDescComp
import com.triforce.malacprodavac.presentation.profile.profilePrivate.ProfilePrivateViewModel
import com.triforce.malacprodavac.presentation.profile.profilePrivate.components.AdvertisingProductButton
import com.triforce.malacprodavac.presentation.profile.profilePrivate.components.MyProductsButton
import com.triforce.malacprodavac.presentation.profile.profilePrivate.components.ProductOptions
import com.triforce.malacprodavac.ui.theme.MP_White

@Composable
fun ShopPrivateScreen(
    navController: NavController,
    viewModel: ProfilePrivateViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val user = state.currentUser

    if (!viewModel.isLoggedIn()) {
        LaunchedEffect(key1 = viewModel.isLoggedIn())
        {
            navController.navigate(Screen.LoginScreen.route) {
                this.popUpTo(Screen.LoginScreen.route)
            }
        }
    }
    Box(
        modifier = Modifier
            .background(MP_White)
            .fillMaxSize()
    ) {
        Column {
            ProfilePrivateHeroComp(user, navController, viewModel, true)
            Spacer(modifier = Modifier.padding(16.dp))

            ShopDescComp(user)
            Spacer(modifier = Modifier.padding(16.dp))

            ProductOptions(null, navController, false)
            Spacer(modifier = Modifier.padding(12.dp))

            MyProductsButton(navController)
            Spacer(modifier = Modifier.padding(16.dp))

            AdvertisingProductButton(Modifier, null, navController, false, false)
            Spacer(modifier = Modifier.padding(16.dp))

            if (user?.shop?.products != null) {
                ShowHighlightSectionComp(
                    navController = navController,
                    products = user.shop.products,
                    title = "Naši proizvodi",
                    route = Screen.HighlightSection.route
                )
                Spacer(modifier = Modifier.padding(16.dp))
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
                    isActive = true
                ),
                BottomNavigationMenuContent(
                    title = "Korpa",
                    graphicID = Icons.Default.ShoppingCart,
                    screen = Screen.CartScreen,
                    isActive = false
                )
            ), modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}