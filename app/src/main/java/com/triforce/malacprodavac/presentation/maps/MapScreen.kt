package com.triforce.malacprodavac.presentation.maps

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
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
import com.triforce.malacprodavac.ui.theme.MP_Orange_Dark
import com.triforce.malacprodavac.ui.theme.MP_White

@Composable
fun MapScreen(

    navController: NavController,
    viewModel: MapsViewModel = hiltViewModel()

) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

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

    val shopIconVector: ImageVector = ImageVector.vectorResource(id = R.drawable.shop_icon)

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
        },
        content = { padding ->
            Box(
                modifier = Modifier.padding(padding)
            ) {
                GoogleMap(
                    cameraPositionState = CameraPositionState(
                        position = cameraPositionState.value
                    ),
                    properties = viewModel.state.properties,
                    uiSettings = uiSettings,
                    modifier = Modifier
                        .fillMaxSize(),
                    onMapClick = {
                        viewModel.onEvent(MapEvent.OnMapLongClick(it))
                        Toast
                            .makeText(
                                context,
                                "Izabrali ste lokaciju!",
                                Toast.LENGTH_LONG
                            )
                            .show()
                    }

                ) {
                    cameraPositionState.value = CameraPosition(
                        cameraPositionState.value.target,
                        cameraPositionState.value.zoom,
                        cameraPositionState.value.tilt,
                        cameraPositionState.value.bearing
                    )

                    if (viewModel.state.selectedAddressLatitude != null && viewModel.state.selectedAddressLongitude != null) {
                        Marker(
                            position = LatLng(
                                viewModel.state.selectedAddressLatitude!!,
                                viewModel.state.selectedAddressLongitude!!
                            ),
                            title = "Nova lokacija",
                            snippet = "",
                            onClick = {
                                cameraPositionState.value = CameraPosition(
                                    cameraPositionState.value.target,
                                    cameraPositionState.value.zoom,
                                    cameraPositionState.value.tilt,
                                    cameraPositionState.value.bearing
                                )

                                //it.showInfoWindow()
                                true
                            }
                        )
                    }


                    viewModel.state.shops!!.forEach { shop ->
                        if (shop.availableAtLatitude != null && shop.availableAtLongitude != null) {
                            Marker(
                                position = LatLng(
                                    shop.availableAtLatitude,
                                    shop.availableAtLongitude
                                ),
                                title = shop.businessName + "user id " + shop.user?.id + " shop id " + shop.id,
                                snippet = if (shop.user != null) {
                                    shop.user.firstName + " " + shop.user.lastName
                                } else {
                                    ""
                                },
                                /*onInfoWindowLongClick = {
                                    viewModel.onEvent(MapEvent.OnInfoWindowLongClick(shop))
                                },*/
                                onClick = {
                                    cameraPositionState.value = CameraPosition(
                                        cameraPositionState.value.target,
                                        cameraPositionState.value.zoom,
                                        cameraPositionState.value.tilt,
                                        cameraPositionState.value.bearing
                                    )

                                    viewModel.onEvent(MapEvent.OnInfoWindowLongClick(shop))
                                    //it.showInfoWindow()
                                    true
                                },
                                icon = bitmapDescriptorFromVector(
                                    LocalContext.current,
                                    R.drawable.shop_icon
                                )
                            )
                        }
                    }
                }
                FloatingActionButton(
                    onClick = {
                        cameraPositionState.value = CameraPosition(
                            cameraPositionState.value.target,
                            cameraPositionState.value.zoom,
                            cameraPositionState.value.tilt,
                            cameraPositionState.value.bearing
                        )

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
                            Icons.Outlined.LocationOn
                        },
                        contentDescription = "Toggle Special map",
                        tint = MP_White
                    )
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    onClick = {}) {
                    Text("Postavi lokaciju")
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