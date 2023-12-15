package com.triforce.malacprodavac.presentation.myProducts

import android.net.Uri
import com.triforce.malacprodavac.domain.model.User
import com.triforce.malacprodavac.domain.model.products.Product

data class MyProductsState(
    val isLoading: Boolean = false,

    val user: User? = null,

    val profileImageUrl: String? = null,
    val profileImageKey: String? = null,

    val token: String? = null,

    var mediaUri: Uri? = null,
    var newImage: Boolean = false,

    val products: List<Product>? = emptyList(),
)