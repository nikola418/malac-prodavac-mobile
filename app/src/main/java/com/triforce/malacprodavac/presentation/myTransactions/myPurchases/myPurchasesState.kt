package com.triforce.malacprodavac.presentation.myTransactions.myPurchases

import com.triforce.malacprodavac.domain.model.Order

data class MyPurchasesState(
    val orders: List<Order> = emptyList(),
    val isLoading: Boolean = false,
)