package com.triforce.malacprodavac.presentation.maps

import com.google.maps.android.compose.MapProperties
import com.triforce.malacprodavac.domain.model.shops.Shop

data class MapState(
    val properties: MapProperties = MapProperties(),
    val isSpecialMap: Boolean = false,

    val showShopDetails: Boolean = false,
    val selectedShop: Shop? = null,

    val shops: List<Shop>? = emptyList(),
    val isLoading: Boolean = false,

    val selectedAddressLatitude: Double? = null,
    val selectedAddressLongitude: Double? = null,
    val selectedAvailableAddressLatitude: Double? = null,
    val selectedAvailableAddressLongitude: Double? = null,
    val selectedStartRouteLatitude: Double? = null,
    val selectedStartRouteLongitude: Double? = null,
    val selectedEndRouteLatitude: Double? = null,
    val selectedEndRouteLongitude: Double? = null,

    val isLocation: Boolean = false,
    val isAvailable: Boolean = false,
    val isRoute: Boolean = false
)