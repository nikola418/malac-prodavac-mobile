package com.triforce.malacprodavac.presentation.myTransactions.myDeliveries

import com.triforce.malacprodavac.util.enum.OrderStatus

sealed class MyDeliveriesEvent {
    data class OrderStatusChanged(val orderId: Int, val orderStatus: OrderStatus) :
        MyDeliveriesEvent()

    data class Submit(val orderId: Int) : MyDeliveriesEvent()
}