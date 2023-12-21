package com.triforce.malacprodavac.presentation.profile.profilePrivate.userScreens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.triforce.malacprodavac.BottomNavigationMenuContent
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.domain.util.enum.DaysOfTheWeek
import com.triforce.malacprodavac.domain.util.enum.UserRole
import com.triforce.malacprodavac.domain.util.enum.WorkTimeEnd
import com.triforce.malacprodavac.domain.util.enum.WorkTimeStart
import com.triforce.malacprodavac.presentation.add_edit_product.components.AddEditTextField
import com.triforce.malacprodavac.presentation.components.BottomNavigationMenu
import com.triforce.malacprodavac.presentation.components.ShowHighlightSectionComp
import com.triforce.malacprodavac.presentation.maps.components.Cordinates
import com.triforce.malacprodavac.presentation.profile.components.ProfilePrivateHeroComp
import com.triforce.malacprodavac.presentation.profile.components.ShopDescComp
import com.triforce.malacprodavac.presentation.profile.profilePrivate.ProfilePrivateEvent
import com.triforce.malacprodavac.presentation.profile.profilePrivate.ProfilePrivateViewModel
import com.triforce.malacprodavac.presentation.profile.profilePrivate.components.AdvertisingProductButton
import com.triforce.malacprodavac.presentation.profile.profilePrivate.components.DropDownListWorkTime
import com.triforce.malacprodavac.presentation.profile.profilePrivate.components.MyProductsButton
import com.triforce.malacprodavac.presentation.profile.profilePrivate.components.ProductOptions
import com.triforce.malacprodavac.presentation.registration.RegistrationFormEvent
import com.triforce.malacprodavac.presentation.registration.components.DropDownList
import com.triforce.malacprodavac.ui.theme.MP_Black
import com.triforce.malacprodavac.ui.theme.MP_Green
import com.triforce.malacprodavac.ui.theme.MP_White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopPrivateScreen(
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
                ),
            )
        },
        modifier = Modifier
            .background(MP_White)
    ) { it ->
        Box(
            Modifier
                .fillMaxSize()
                .background(MP_White)
                .padding(it)
        ) {
            if (!state.isEditing) {
                Column {
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
            } else {
                Column(
                    modifier = Modifier
                        .verticalScroll(state = scrollState)
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(Modifier.height(8.dp))
                    AddEditTextField(
                        text = state.updateUser?.firstName ?: "",
                        isError = false,
                        onTextValueChange = {
                            viewModel.onEvent(ProfilePrivateEvent.FirstNameChanged(it))
                        },
                        placeholder = "Ime"
                    )
                    Spacer(Modifier.height(8.dp))
                    AddEditTextField(
                        text = state.updateUser?.lastName ?: "",
                        isError = false,
                        onTextValueChange = {
                            viewModel.onEvent(ProfilePrivateEvent.LastNameChanged(it))
                        },
                        placeholder = "Prezime"
                    )
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
                        isError = false,
                        onTextValueChange = {
                            viewModel.onEvent(ProfilePrivateEvent.PhoneNumberChanged(it))
                        },
                        placeholder = "Kontakt telefon"
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = { navController.navigate(Screen.MapScreen.route) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = "Postavite Vašu Lokaciju")
                    }
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = { navController.navigate(Screen.MapScreen.route) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = "Postavite Rutu Obilaska")
                    }
                    AddEditTextField(
                        text = state.updateShop?.businessName ?: "",
                        isError = false,
                        onTextValueChange = {
                            viewModel.onEvent(ProfilePrivateEvent.BusinessNameChanged(it))
                        },
                        placeholder = "Ime prodavnice"
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Radni dani:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MP_Black
                    )
                    DropDownListWorkTime(
                        entries = enumValues<DaysOfTheWeek>().toList(),
                        handleSelect = { nzm ->
                            viewModel.onEvent(
                                ProfilePrivateEvent.OpenFromDaysChanged(nzm as DaysOfTheWeek))
                        },
                        label = "Prvi radni dan")
                    Spacer(Modifier.height(8.dp))
                    DropDownListWorkTime(
                        entries = enumValues<DaysOfTheWeek>().toList(),
                        handleSelect = { nzm ->
                            viewModel.onEvent(
                                ProfilePrivateEvent.OpenTillDaysChanged(nzm as DaysOfTheWeek))
                        },
                        label = "Zadnji radni dan")
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Radno vreme:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold, color = MP_Black
                    )
                    DropDownListWorkTime(
                        entries = enumValues<WorkTimeStart>().toList(),
                        handleSelect = { nzm ->
                            viewModel.onEvent(
                                ProfilePrivateEvent.OpenFromChanged(nzm as WorkTimeStart))
                        },
                        label = "Od")
                    Spacer(Modifier.height(8.dp))
                    DropDownListWorkTime(
                        entries = enumValues<WorkTimeEnd>().toList(),
                        handleSelect = { nzm ->
                            viewModel.onEvent(
                                ProfilePrivateEvent.OpenTillChanged(nzm as WorkTimeEnd))
                        },
                        label = "Do")
                    Spacer(Modifier.height(8.dp))
                    AddEditTextField(
                        text = state.updateShop?.availableAt ?: "",
                        isError = false,
                        onTextValueChange = {
                            viewModel.onEvent(ProfilePrivateEvent.AvailableAtChanged(it))
                        },
                        placeholder = "Lokacija izlaganja proizvoda"
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = { navController.navigate(Screen.MapScreen.route) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = "Postavite Lokaciju Izlaganja")
                    }
                    Button(
                        onClick = {
                            state.updateUser?.addressLatitude = Cordinates.latitude
                            state.updateUser?.addressLongitude = Cordinates.longitude
                            state.updateShop?.availableAtLatitude = Cordinates.latitude
                            state.updateShop?.availableAtLongitude = Cordinates.longitude

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
                        Text(
                            text = "Potvrdi izmene",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}