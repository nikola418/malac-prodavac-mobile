package com.triforce.malacprodavac.presentation.orders

import com.triforce.malacprodavac.domain.model.Order
import com.triforce.malacprodavac.domain.model.products.Product

data class OrderState(

    val orders: List<Order> = emptyList(),
    val isLoading: Boolean = false,
    val product: Product? = null,
    val order: Order? = null,
    val products: MutableList<Product> = mutableListOf()
)
