package com.triforce.malacprodavac.presentation.store.category

import com.triforce.malacprodavac.domain.model.User
import com.triforce.malacprodavac.domain.model.products.Product

data class CategoryState(
    val id: Int = -1,
    val categoryId: Int? = null,

    val title: String = "",
    val color: String = "",

    val searchQuery: String = "",

    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,

    val token: String? = null,
    val user: User? = null,
    val profileImageUrl: String? = null,
    val profileImageKey: String? = null,

    val currentPage: Int = 1,
    val isLastPage: Boolean = true,
    val perPage: Int = 20
)