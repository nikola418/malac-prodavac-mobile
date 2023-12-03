package com.triforce.malacprodavac.presentation.maps

import android.app.PendingIntent.getActivity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
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
import com.triforce.malacprodavac.ui.theme.MP_White

@Composable
fun MapScreen(

    navController: NavController,
    viewModel: MapsViewModel = hiltViewModel()

) {
    val scaffoldState = rememberScaffoldState()

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
        /*floatingActionButton = {
                FloatingActionButton(
                    onClick = { viewModel.onEvent(MapEvent.ToggleSpecialMap) }
                ) {
                    Icon(
                        imageVector = if (viewModel.state.isSpecialMap) {
                            Icons.Outlined.Clear
                        } else {
                            Icons.Outlined.LocationOn
                        },
                        contentDescription = "Toggle Special map"
                    )
                }
            },*/
        content = { padding ->
            Box(
                modifier = Modifier.padding(padding)
            ) {
                GoogleMap(
                    cameraPositionState = CameraPositionState(
                        position = CameraPosition(
                            LatLng(44.01667, 20.91667),
                            12.0f,
                            0.0f,
                            0.0f
                        )
                    ),
                    properties = viewModel.state.properties,
                    uiSettings = uiSettings,
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize(),
                    onMapLongClick = {
                        viewModel.onEvent(MapEvent.OnMapLongClick(it))
                    }
                ) {
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
                        viewModel.onEvent(MapEvent.ToggleSpecialMap)
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = if (viewModel.state.isSpecialMap) {
                            Icons.Outlined.Clear
                        } else {
                            Icons.Outlined.LocationOn
                        },
                        contentDescription = "Toggle Special map"
                    )
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
                            isActive = true
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
                    ), modifier = Modifier.align(Alignment.BottomCenter)
                )
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