package com.triforce.malacprodavac.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.triforce.malacprodavac.BottomNavigationMenuContent
import com.triforce.malacprodavac.Feature
import com.triforce.malacprodavac.LinearGradient
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.presentation.FavProducts.FavoriteViewModel
import com.triforce.malacprodavac.presentation.components.BottomNavigationMenu
import com.triforce.malacprodavac.presentation.components.RoundedBackgroundComp
import com.triforce.malacprodavac.presentation.home.components.GreetingSection
import com.triforce.malacprodavac.presentation.home.components.RecommendedFeaturesSection
import com.triforce.malacprodavac.presentation.orders.OrderViewModel
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_GreenDark
import com.triforce.malacprodavac.ui.theme.MP_Pink
import com.triforce.malacprodavac.ui.theme.MP_White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    viewModelOrder: OrderViewModel = hiltViewModel()
) {
    val state = viewModel.state

    val viewModelFavProducts: FavoriteViewModel = hiltViewModel()
    val stateFav by remember { mutableStateOf(viewModelFavProducts.state) }

    val user = state.currentUser
    var role = "Kupac"

    var features = listOf(
        Feature(
            id = 1,
            title = "Otvori Market",
            graphicID = ImageVector.vectorResource(R.drawable.logo_green),
            color1 = MP_Green,
            color2 = MP_Green,
            screen = Screen.StoreScreen
        ),
        Feature(
            id = 1,
            title = "Korpa",
            graphicID = Icons.Default.ShoppingCart,
            color1 = MP_GreenDark,
            color2 = MP_GreenDark,
            screen = Screen.CartScreen
        ),
        Feature(
            id = 1,
            title = "Pretraži Mape",
            graphicID = Icons.Default.LocationOn,
            color1 = MP_Green,
            color2 = MP_Green,
            screen = Screen.MapScreen
        ),
        Feature(
            id = 1,
            title = "Profil",
            graphicID = Icons.Default.Person,
            color1 = MP_GreenDark,
            color2 = MP_GreenDark,
            screen = Screen.PrivateProfile
        ),
        Feature(
            id = 1,
            title = "Porudžbine",
            graphicID = ImageVector.vectorResource(R.drawable.round_featured_play_list_24),
            color1 = MP_Green,
            color2 = MP_Green,
            screen = Screen.OrderScreen
        ),
        Feature(
            id = 1,
            title = "Omiljeni proizvodi",
            graphicID = Icons.Default.Favorite,
            color1 = MP_GreenDark,
            color2 = MP_GreenDark,
            screen = Screen.FavoriteProductsScreen
        ),
        Feature(
            id = 1,
            title = "Moje prodaje",
            graphicID = Icons.Default.Favorite,
            color1 = MP_Green,
            color2 = MP_Green,
            screen = Screen.MySales
        ),
        Feature(
            id = 1,
            title = "Moje kupovine",
            graphicID = Icons.Default.Favorite,
            color1 = MP_Green,
            color2 = MP_Green,
            screen = Screen.MyPurchases
        ),
        Feature(
            id = 1,
            title = "Istorija transakcija",
            graphicID = Icons.Default.Favorite,
            color1 = MP_Green,
            color2 = MP_Green,
            screen = Screen.TransactionHistory
        )
    )

    if (user != null) {
        if (user.roles.contains("Shop") == false && user.roles.contains("Courier")) {
            role = "Kurir"
        } else if (user.roles.contains("Shop")) {
            role = "Prodavac"
            features = listOf(
                Feature(
                    id = 1,
                    title = "Moji Proizvodi",
                    graphicID = ImageVector.vectorResource(R.drawable.logo_green),
                    color1 = MP_Pink,
                    color2 = MP_Pink,
                    screen = Screen.MyProductsScreen
                ),
                Feature(
                    id = 1,
                    title = "Dodaj novi proizvod",
                    graphicID = Icons.Default.Add,
                    color1 = MP_Pink,
                    color2 = MP_Pink,
                    screen = Screen.AddProduct
                )
            ) + features
        }
    }
    if (!state.isLoading) {
        Scaffold(
            modifier = Modifier
                .background(MP_White)
                .fillMaxSize(),
            bottomBar = {
                BottomNavigationMenu(
                    navController = navController,
                    items = listOf(
                        BottomNavigationMenuContent(
                            title = "Početna",
                            graphicID = Icons.Default.Home,
                            screen = Screen.HomeScreen,
                            isActive = true
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
                            isActive = false
                        )
                    )
                )
            }
        ) { it ->
            LinearGradient(color1 = MP_Green, color2 = MP_Green)
            RoundedBackgroundComp(top = 100.dp, color = MP_White)

            Column {
                GreetingSection(
                    msg = "Malac ${role}",
                    subMsg = "Od sirupa do sira",
                    navController = navController
                )
                Spacer(modifier = Modifier.padding(16.dp))

                RecommendedFeaturesSection(
                    navController = navController,
                    features = features
                )
            }

        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}