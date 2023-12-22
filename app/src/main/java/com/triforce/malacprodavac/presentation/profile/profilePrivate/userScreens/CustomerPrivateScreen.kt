package com.triforce.malacprodavac.presentation.profile.profilePrivate.userScreens

import android.widget.Toast
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.triforce.malacprodavac.BottomNavigationMenuContent
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.presentation.add_edit_product.components.AddEditTextField
import com.triforce.malacprodavac.presentation.components.BottomNavigationMenu
import com.triforce.malacprodavac.presentation.maps.components.Cordinates
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
    val context = LocalContext.current

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
                        text = state.updateUser?.firstName ?: "",
                        isError = state.firstNameError != null,
                        onTextValueChange = {
                            viewModel.onEvent(ProfilePrivateEvent.FirstNameChanged(it))
                        },
                        placeholder = "Ime"
                    )
                    if (state.firstNameError != null) {
                    Text(
                        text = state.firstNameError,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
                    Spacer(Modifier.height(8.dp))
                    AddEditTextField(
                        text = state.updateUser?.lastName ?: "",
                        isError = state.lastNameError != null,
                        onTextValueChange = {
                            viewModel.onEvent(ProfilePrivateEvent.LastNameChanged(it))
                        },
                        placeholder = "Prezime"
                    )
                    if (state.lastNameError != null) {
                        Text(
                            text = state.lastNameError,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    AddEditTextField(
                        text = state.updateUser?.address ?: "",
                        isError = false,
                        onTextValueChange = {
                            viewModel.onEvent(ProfilePrivateEvent.AddressChanged(it))
                        },
                        placeholder = "Adresa"
                    )
                    Spacer(Modifier.height(8.dp))
                    AddEditTextField(
                        text = state.updateUser?.phoneNumber ?: "",
                        isError = state.phoneNumberError != null,
                        onTextValueChange = {
                            viewModel.onEvent(ProfilePrivateEvent.PhoneNumberChanged(it))
                        },
                        placeholder = "Kontakt telefon"
                    )
                    if (state.phoneNumberError != null) {
                        Text(
                            text = state.phoneNumberError,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                    Button(
                        onClick = {
                            Cordinates.isLocation = true
                            Cordinates.isAvailable = false
                            navController.navigate(Screen.MapScreen.route) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = "Postavite Lokaciju")
                    }
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = {
                            state.updateUser?.addressLatitude = Cordinates.latitude
                            state.updateUser?.addressLongitude = Cordinates.longitude
                            viewModel.onEvent(ProfilePrivateEvent.SubmitEdit)
                            Toast
                                .makeText(
                                    context,
                                    "Uspešno ste izmenili podatke!",
                                    Toast.LENGTH_LONG
                                )
                                .show()
                                  },
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
