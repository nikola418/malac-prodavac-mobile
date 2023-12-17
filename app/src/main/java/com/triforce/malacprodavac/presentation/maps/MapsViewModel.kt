package com.triforce.malacprodavac.presentation.maps

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.MapStyleOptions
import com.triforce.malacprodavac.domain.model.shops.Shop
import com.triforce.malacprodavac.domain.repository.ShopRepository
import com.triforce.malacprodavac.domain.util.Resource
import com.triforce.malacprodavac.domain.util.filter.Filter
import com.triforce.malacprodavac.domain.util.filter.FilterBuilder
import com.triforce.malacprodavac.presentation.maps.styles.MapStyle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(

    private val repository: ShopRepository,

    ) : ViewModel() {

    var state by mutableStateOf(MapState())

    init {
        getShops()
    }

    fun onEvent(event: MapEvent) {
        when (event) {
            MapEvent.ToggleSpecialMap -> {
                state = state.copy(
                    properties = state.properties.copy(
                        mapStyleOptions = if (state.isSpecialMap) {
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

            is MapEvent.OnMapLongClick -> {}
            is MapEvent.OnMapClick -> {}

        }
    }

    private fun getShops() {

        viewModelScope.launch {

            val query = FilterBuilder.buildFilterQueryMap(
                Filter(filter = null, order = null, limit = null, offset = null)
            )

            repository.getShops(true, query).collectLatest { result ->
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