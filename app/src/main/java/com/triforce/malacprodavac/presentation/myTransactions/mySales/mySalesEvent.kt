package com.triforce.malacprodavac.presentation.myTransactions.mySales

sealed class MySalesEvent {
    data class CourierIdChanged(val courierId: Int, val orderId: Int) : MySalesEvent()
    data class Submit(val orderId: Int) : MySalesEvent()
    data class AcceptOrder(val orderId: Int) : MySalesEvent()
}