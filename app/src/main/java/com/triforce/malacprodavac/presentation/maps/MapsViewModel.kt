package com.triforce.malacprodavac.presentation.maps

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.MapStyleOptions
import com.triforce.malacprodavac.domain.util.filter.Filter
import com.triforce.malacprodavac.domain.util.filter.FilterBuilder
import com.triforce.malacprodavac.domain.model.shops.Shop
import com.triforce.malacprodavac.domain.repository.ShopRepository
import com.triforce.malacprodavac.domain.util.Resource
import com.triforce.malacprodavac.presentation.maps.components.Cordinates
import com.triforce.malacprodavac.presentation.maps.styles.MapStyle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(

    private val repository: ShopRepository,
    savedStateHandle: SavedStateHandle

): ViewModel() {

    var state by mutableStateOf(MapState())

    init {
        getShops(true)
    }

    fun onEvent(event: MapEvent){
        when(event){
            MapEvent.ToggleSpecialMap -> {
                state = state.copy(
                    properties = state.properties.copy(
                        mapStyleOptions = if(state.isSpecialMap) {
                            null
                        } else {
                            MapStyleOptions(MapStyle.json)
                        },
                    ),
                    isSpecialMap = !state.isSpecialMap
                )
            }

            is MapEvent.OnInfoWindowLongClick -> {
                state = state.copy(
                    selectedShop = event.shop,
                    showShopDetails = !state.showShopDetails
                )
            }

            is MapEvent.OnMapLongClick -> {
                state = state.copy(
                    selectedAddressLatitude = event.latLng.latitude,
                    selectedAddressLongitude = event.latLng.longitude
                )
                Cordinates.latitude = state.selectedAddressLatitude
                Cordinates.longitude = state.selectedAddressLongitude
            }

            is MapEvent.OnMapAvailableLongClick -> {
                state = state.copy(
                    selectedAvailableAddressLatitude = event.latLng.latitude,
                    selectedAvailableAddressLongitude = event.latLng.longitude
                )
                Cordinates.availableAtLatitude = state.selectedAvailableAddressLatitude
                Cordinates.availableAtLongitude = state.selectedAvailableAddressLongitude
            }
            else -> { }
        }
    }

    private fun getShops(fetchFromRemote: Boolean, searchText: String = "") {

        viewModelScope.launch {

            val query = FilterBuilder.buildFilterQueryMap(
                Filter(filter = null, order = null, limit = null, offset = null )
            )

            repository.getShops(fetchFromRemote, query).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data is List<Shop>) {
                            state = state.copy(shops = result.data)
                        }
                    }

                    is Resource.Error -> {
                        Unit
                    }

                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = result.isLoading
                        )
                    }
                }
            }
        }
    }
}