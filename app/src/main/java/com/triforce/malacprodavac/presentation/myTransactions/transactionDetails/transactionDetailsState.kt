package com.triforce.malacprodavac.presentation.myTransactions.transactionDetails

import com.triforce.malacprodavac.domain.model.Order
import com.triforce.malacprodavac.domain.model.User

data class TransactionDetailsState(
    val order: Order? = null,
    val orderId: Int = -1,
    val user: User? = null,
    val token: String? = null,
    val isLoading: Boolean = false,
)