package com.triforce.malacprodavac.presentation.maps

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Style
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.triforce.malacprodavac.BottomNavigationMenuContent
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.presentation.components.BottomNavigationMenu
import com.triforce.malacprodavac.presentation.maps.components.BottomMapShopDetails
import com.triforce.malacprodavac.presentation.maps.components.Cordinates
import com.triforce.malacprodavac.presentation.profile.profilePrivate.ProfilePrivateEvent
import com.triforce.malacprodavac.presentation.profile.profilePrivate.ProfilePrivateViewModel
import com.triforce.malacprodavac.ui.theme.MP_Orange_Dark
import com.triforce.malacprodavac.ui.theme.MP_White

@Composable
fun MapScreen(

    navController: NavController,
    viewModel: MapsViewModel = hiltViewModel(),
    viewModelProfilePrivate: ProfilePrivateViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val state = viewModelProfilePrivate.state

    val initialCameraPosition = remember {
        CameraPosition(
            LatLng(44.01667, 20.91667),
            12.0f,
            0.0f,
            0.0f
        )
    }
    val cameraPositionState = remember {
        mutableStateOf(initialCameraPosition)
    }

    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }

    fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        content = { padding ->
            Box(
                modifier = Modifier.padding(padding)
            ) {
                GoogleMap(
                    cameraPositionState = CameraPositionState(position = cameraPositionState.value),
                    properties = viewModel.state.properties,
                    uiSettings = uiSettings,
                    modifier = Modifier.fillMaxSize(),
                    onMapClick = {
                        if (Cordinates.isLocation) {
                            viewModel.onEvent(MapEvent.OnMapLongClick(it))
                        }
                        if (Cordinates.isAvailable) {
                            viewModel.onEvent(MapEvent.OnMapAvailableLongClick(it))
                        }
                        if (Cordinates.isRoute) {
                            if (Cordinates.startRoute) {
                                viewModel.onEvent(MapEvent.OnMapClickStartRoute(it))
                                Cordinates.startRoute = false
                            } else {
                                viewModel.onEvent(MapEvent.OnMapClickEndRoute(it))
                                Cordinates.startRoute = true
                            }
                        }
                    }
                ) {
                    if (viewModel.state.selectedAddressLatitude != null && viewModel.state.selectedAddressLongitude != null) {
                        Marker(
                            position = LatLng(
                                viewModel.state.selectedAddressLatitude!!,
                                viewModel.state.selectedAddressLongitude!!
                            ),
                            title = "Nova lokacija",
                            snippet = "Nova lokacija",
                            onClick = {
                                true
                            }
                        )
                    }
                    if (viewModel.state.selectedAvailableAddressLatitude != null && viewModel.state.selectedAvailableAddressLongitude != null) {
                        Marker(
                            position = LatLng(
                                viewModel.state.selectedAvailableAddressLatitude!!,
                                viewModel.state.selectedAvailableAddressLongitude!!
                            ),
                            title = "Oglašavanje proizvoda",
                            onClick = {
                                true
                            }
                        )
                    }
                    if (viewModel.state.selectedStartRouteLatitude != null && viewModel.state.selectedStartRouteLongitude != null) {
                        Marker(
                            position = LatLng(
                                viewModel.state.selectedStartRouteLatitude!!,
                                viewModel.state.selectedStartRouteLongitude!!
                            ),
                            title = "Početna ruta",
                            onClick = {
                                true
                            }
                        )
                    }
                    if (viewModel.state.selectedEndRouteLatitude != null && viewModel.state.selectedEndRouteLongitude != null) {
                        Marker(
                            position = LatLng(
                                viewModel.state.selectedEndRouteLatitude!!,
                                viewModel.state.selectedEndRouteLongitude!!
                            ),
                            title = "Krajnja ruta",
                            onClick = {
                                true
                            }
                        )
                    }
                    viewModel.state.shops!!.forEach { shop ->

                        val shopLatLng =
                            shop.user?.addressLatitude?.let { addressLatitude ->
                                shop.user.addressLongitude?.let { addressLongitude ->
                                    LatLng(addressLatitude, addressLongitude)
                                }
                            }

                        val advertisingLatLng =
                            shop.availableAtLatitude?.let { availableAtLatitude ->
                                shop.availableAtLongitude?.let { availableAtLongitude ->
                                    LatLng(availableAtLatitude, availableAtLongitude)
                                }
                            }

                        if (shopLatLng != null) {
                            Marker(
                                position = shopLatLng,
                                title = "ShopMarker",
                                onClick = {
                                    viewModel.onEvent(MapEvent.OnInfoWindowLongClick(shop))
                                    true
                                },
                                icon = bitmapDescriptorFromVector(
                                    LocalContext.current,
                                    R.drawable.shop_icon
                                )
                            )
                        }

                        if (shopLatLng != null && advertisingLatLng != null && shopLatLng != advertisingLatLng) {
                            Marker(
                                position = advertisingLatLng,
                                title = "AdvertisingMarker",
                                onClick = {
                                    viewModel.onEvent(MapEvent.OnInfoWindowLongClick(shop))
                                    true
                                },
                                icon = bitmapDescriptorFromVector(
                                    LocalContext.current,
                                    R.drawable.logo_green
                                )
                            )
                        }
                    }
                }
                FloatingActionButton(
                    onClick = {
                        viewModel.onEvent(MapEvent.ToggleSpecialMap)
                    },
                    backgroundColor = MP_Orange_Dark,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(27.dp)
                ) {
                    Icon(
                        imageVector = if (viewModel.state.isSpecialMap) {
                            Icons.Outlined.Clear
                        } else {
                            Icons.Outlined.Style
                        },
                        contentDescription = "Toggle Special map",
                        tint = MP_White
                    )
                }

                Column(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (Cordinates.isLocation || Cordinates.isAvailable || Cordinates.isRoute) {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MP_Orange_Dark,
                                contentColor = MP_White
                            ),
                            modifier = Modifier
                                .fillMaxWidth(0.9F)
                                .padding(bottom = 16.dp)
                                .clip(
                                    RoundedCornerShape(20.dp)
                                ),
                            onClick = {

                                state.updateUser?.addressLatitude = Cordinates.latitude
                                state.updateUser?.addressLongitude = Cordinates.longitude

                                state.updateShop?.availableAtLatitude =
                                    Cordinates.availableAtLatitude
                                state.updateShop?.availableAtLongitude =
                                    Cordinates.availableAtLongitude

                                state.updateCourier?.routeStartLatitude =
                                    Cordinates.startRouteLatitude
                                state.updateCourier?.routeStartLongitude =
                                    Cordinates.startRouteLongitude

                                state.updateCourier?.routeEndLatitude = Cordinates.endRouteLatitude
                                state.updateCourier?.routeEndLongitude =
                                    Cordinates.endRouteLongitude

                                if (Cordinates.isLocation) {
                                    Toast
                                        .makeText(
                                            context,
                                            "Postavili ste uspešno lokaciju!",
                                            Toast.LENGTH_LONG
                                        )
                                        .show()
                                } else if (Cordinates.isAvailable) {
                                    Toast
                                        .makeText(
                                            context,
                                            "Postavili ste uspešno lokaciju oglašavanja proizvoda!",
                                            Toast.LENGTH_LONG
                                        )
                                        .show()
                                } else {
                                    Toast
                                        .makeText(
                                            context,
                                            "Postavili ste uspešno rutu obilaska!",
                                            Toast.LENGTH_LONG
                                        )
                                        .show()
                                }

                                viewModelProfilePrivate.onEvent(ProfilePrivateEvent.SubmitEdit)
                                navController.popBackStack()
                            }

                        ) {
                            var text = "Missing Text"

                            if (Cordinates.isLocation)
                                text = "Postavi svoju lokaciju"
                            else if (Cordinates.isAvailable)
                                text = "Postavi lokaciju izlaganja proizvoda"
                            else if (Cordinates.isRoute)
                                text = "Postavi rutu obilaska"

                            Text(
                                text = text,
                                style = MaterialTheme.typography.body1,
                                fontWeight = FontWeight.W500,
                                modifier = Modifier.padding(vertical = 6.dp)
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
                                isActive = false
                            )
                        )
                    )
                }
            }

            viewModel.state.selectedShop?.let { selectedShop ->
                BottomMapShopDetails(
                    selectedShop,
                    viewModel.state.showShopDetails,
                    navController
                )
            }
        }
    )
}