package com.triforce.malacprodavac.presentation.profile.profilePrivate.userScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.triforce.malacprodavac.BottomNavigationMenuContent
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.presentation.add_edit_product.components.AddEditTextField
import com.triforce.malacprodavac.presentation.components.BottomNavigationMenu
import com.triforce.malacprodavac.presentation.profile.components.ProfilePrivateHeroComp
import com.triforce.malacprodavac.presentation.profile.profilePrivate.ProfilePrivateEvent
import com.triforce.malacprodavac.presentation.profile.profilePrivate.ProfilePrivateViewModel
import com.triforce.malacprodavac.presentation.profile.profilePrivate.components.CourierDescComp
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerPrivateScreen(
    navController: NavController,
    viewModel: ProfilePrivateViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val user = state.currentUser
    val scrollState = rememberScrollState()

    if (!viewModel.isLoggedIn()) {
        LaunchedEffect(key1 = viewModel.isLoggedIn())
        {
            navController.navigate(Screen.LoginScreen.route) {
                this.popUpTo(Screen.LoginScreen.route)
            }
        }
    }
    Scaffold(
        topBar = {
            ProfilePrivateHeroComp(user, navController, viewModel, true)
        },
        bottomBar = {
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
                )
            )
        },
        modifier = Modifier
            .background(MP_White)
    ) { it ->
        Column(
            Modifier
                .fillMaxSize()
                .background(MP_White)
                .padding(it)
        ) {
            Spacer(modifier = Modifier.padding(16.dp))
            if (!state.isEditing) {
                Column {
                    CourierDescComp(user)
                }
            } else {
                Column(
                    modifier = Modifier
                        .verticalScroll(state = scrollState)
                        .padding(horizontal = 16.dp)
                ) {
                    AddEditTextField(
                        text = user?.firstName ?: "",
                        isError = false,
                        onTextValueChange = {
                            viewModel.onEvent(ProfilePrivateEvent.FirstNameChanged(it))
                        },
                        placeholder = "Ime"
                    )
                    Spacer(Modifier.height(8.dp))
                    AddEditTextField(
                        text = user?.lastName ?: "",
                        isError = false,
                        onTextValueChange = {
                            viewModel.onEvent(ProfilePrivateEvent.LastNameChanged(it))
                        },
                        placeholder = "Prezime"
                    )
                    Spacer(Modifier.height(8.dp))
                    AddEditTextField(
                        text = user?.address ?: "",
                        isError = false,
                        onTextValueChange = {
                            viewModel.onEvent(ProfilePrivateEvent.AddressChanged(it))
                        },
                        placeholder = "Adresa"
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = { navController.navigate(Screen.MapScreen.route) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = "Postavite Lokaciju")
                    }
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = { viewModel.onEvent(ProfilePrivateEvent.SubmitEdit) },
                        colors = ButtonDefaults.buttonColors(containerColor = MP_Green),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = "Potvrdi izmene", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }

        }
    }

}
