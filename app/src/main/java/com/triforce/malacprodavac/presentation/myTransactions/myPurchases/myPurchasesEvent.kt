package com.triforce.malacprodavac.presentation.myTransactions.myPurchases

sealed class MyPurchasesEvent {
    data class DeleteOrder(val orderId: Int) : MyPurchasesEvent()
}