package com.triforce.malacprodavac.presentation.myTransactions.myDeliveries

import com.triforce.malacprodavac.domain.model.Order
import com.triforce.malacprodavac.domain.model.User

data class MyDeliveriesState(
    val orders: List<Order> = emptyList(),
    val user: User? = null,
    val token: String? = null,
    val isLoading: Boolean = false,
)