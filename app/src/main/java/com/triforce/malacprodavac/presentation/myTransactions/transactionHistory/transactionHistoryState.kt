package com.triforce.malacprodavac.presentation.myTransactions.transactionHistory

import com.triforce.malacprodavac.domain.model.Order
import com.triforce.malacprodavac.domain.model.User

data class TransactionHistoryState(
    val orders: List<Order> = emptyList(),
    val user: User? = null,
    val token: String? = null,
    val isLoading: Boolean = false,
)