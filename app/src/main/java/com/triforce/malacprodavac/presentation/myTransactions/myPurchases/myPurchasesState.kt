package com.triforce.malacprodavac.presentation.myTransactions.myPurchases

import com.triforce.malacprodavac.domain.model.Order
import com.triforce.malacprodavac.domain.model.User

data class MyPurchasesState(
    val orders: List<Order> = emptyList(),
    val user: User? = null,
    val token: String? = null,
    val isLoading: Boolean = false,
)