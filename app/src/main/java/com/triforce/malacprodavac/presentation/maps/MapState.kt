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
    val selectedAddressLongitude: Double? = null
)