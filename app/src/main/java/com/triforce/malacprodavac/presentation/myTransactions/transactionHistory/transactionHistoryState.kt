package com.triforce.malacprodavac.presentation.myTransactions.transactionHistory

import com.triforce.malacprodavac.domain.model.Order

data class TransactionHistoryState(
    val orders: List<Order> = emptyList(),
    val isLoading: Boolean = false,
)