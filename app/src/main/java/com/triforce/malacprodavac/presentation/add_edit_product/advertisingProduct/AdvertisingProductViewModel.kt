package com.triforce.malacprodavac.presentation.add_edit_product.advertisingProduct

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triforce.malacprodavac.domain.model.products.UpdateProductDto
import com.triforce.malacprodavac.domain.use_case.product.ProductUseCase
import com.triforce.malacprodavac.domain.util.Resource
import com.triforce.malacprodavac.domain.util.compressedFileFromUri
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdvertisingProductViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(AdvertisingProductState())

    init {
        savedStateHandle.get<Int>("productId")?.let { productId ->
            if (productId != -1) {
                getProduct(true, productId)
            }
        }
    }

    fun onEvent(event: AdvertisingProductEvent) {
        when (event) {
            is AdvertisingProductEvent.TitleChanged -> {
                state = state.copy(product = state.product?.copy(title = event.title))
            }

            is AdvertisingProductEvent.DescChanged -> {
                state = state.copy(product = state.product?.copy(desc = event.desc))
            }

            is AdvertisingProductEvent.LocationChanged -> {
                state = state.copy(product = state.product?.copy(availableAt = event.location))
            }

            is AdvertisingProductEvent.StartAdvertisingChanged -> {
                state = state.copy(product = state.product?.copy(availableFromHours = event.start))
            }

            is AdvertisingProductEvent.EndAdvertisingChanged -> {
                state = state.copy(product = state.product?.copy(availableTillHours = event.end))
            }

            is AdvertisingProductEvent.Submit -> {
                state = if (state.product?.availableAt.isNullOrBlank()) {
                    state.copy(locationError = "Unesite lokaciju oglašavanja!")
                } else {
                    state.copy(locationError = null)
                }

                val updateProduct = UpdateProductDto(
                    unitOfMeasurement = state.product?.unitOfMeasurement,
                    currency = state.product?.currency,
                    title = state.product?.title,
                    desc = state.product?.desc,
                    price = state.product?.price,
                    categoryId = state.product?.categoryId,
                    availableAt = state.product?.availableAt,
                    availableAtLatitude = state.product?.availableAtLatitude,
                    availableAtLongitude = state.product?.availableAtLongitude,
                    availableFromHours = state.product?.availableFromHours,
                    availableTillHours = state.product?.availableTillHours,
                    available = state.product?.available
                )

                updateProduct(state.product?.id!!, updateProduct)
            }

            else -> {}
        }
    }

    private fun updateProduct(
        productId: Int,
        updateProduct: UpdateProductDto
    ) {
        viewModelScope.launch {
            productUseCase.updateProduct(productId, updateProduct).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            state = state.copy(product = it, isUpdateSuccessful = true)
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


    private fun getProduct(fetchFromRemote: Boolean, productId: Int) {
        viewModelScope.launch {
            productUseCase.getProductForId(productId, fetchFromRemote).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            state = state.copy(
                                product = result.data,
                                thumbUrl = if (result.data.productMedia != null)
                                    "http://softeng.pmf.kg.ac.rs:10010/products/${result.data.productMedia.productId}/medias/${result.data.productMedia.id}"
                                else null
                            )
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

    private fun changeProductImage(context: Context, uri: Uri) {
        viewModelScope.launch {
            val files = compressedFileFromUri(context, uri)
            productUseCase.setProductImage(state.product?.id!!, files).collect {
                when (it) {
                    is Resource.Error -> {

                    }

                    is Resource.Loading -> {
                        state = state.copy(isLoading = it.isLoading)
                    }

                    is Resource.Success -> {
                        getProduct(true, state.product?.id!!)
                    }
                }
            }
        }
    }
}