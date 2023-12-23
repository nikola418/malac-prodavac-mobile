package com.triforce.malacprodavac.presentation.myTransactions.transactionDetails

sealed class TransactionDetailsEvent {
    data class DeleteOrder(val orderId: Int) : TransactionDetailsEvent()
}