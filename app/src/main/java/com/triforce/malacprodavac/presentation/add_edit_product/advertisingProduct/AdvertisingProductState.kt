package com.triforce.malacprodavac.presentation.add_edit_product.advertisingProduct

import android.net.Uri
import com.triforce.malacprodavac.domain.model.products.Product

data class AdvertisingProductState(
    val productId: Int? = -1,
    val product: Product? = null,
    val isLoading: Boolean = false,
    val isUpdateSuccessful: Boolean = false,

    val thumbUrl: String? = null,

    val errorMessage: String? = null,
    val locationError: String? = null,
    val startHourError: String? = null,
    val EndHourError: String? = null,
)
