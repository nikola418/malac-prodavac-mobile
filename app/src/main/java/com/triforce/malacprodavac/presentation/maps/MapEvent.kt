package com.triforce.malacprodavac.presentation.maps

import com.google.android.gms.maps.model.LatLng
import com.triforce.malacprodavac.domain.model.shops.Shop

sealed class MapEvent {

    object ToggleSpecialMap: MapEvent()
    data class OnMapClick(val latLng: LatLng): MapEvent()
    data class OnMapLongClick(val latLng: LatLng): MapEvent()
    data class OnMapAvailableLongClick(val latLng: LatLng): MapEvent()
    data class OnMapClickStartRoute(val latLngStart: LatLng): MapEvent()
    data class OnMapClickEndRoute(val latLngEnd: LatLng): MapEvent()
    data class OnInfoWindowLongClick(val shop: Shop): MapEvent()
}