package com.triforce.malacprodavac.presentation.myTransactions.mySales

import com.triforce.malacprodavac.domain.model.Order
import com.triforce.malacprodavac.util.enum.OrderStatus

sealed class MySalesEvent {
    data class CourierIdChanged(val courierId: Int, val orderId: Int) : MySalesEvent()
    data class Submit(val order: Order, val orderStatus: OrderStatus) : MySalesEvent()
    data class AcceptOrder(val order: Order) : MySalesEvent()
    data class DeleteOrder(val order: Order) : MySalesEvent()
}