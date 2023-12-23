package com.triforce.malacprodavac.data.mappers

import com.triforce.malacprodavac.data.local.order.OrderEntity
import com.triforce.malacprodavac.domain.model.Order

fun OrderEntity.toOrder(): Order = Order(
    id = id,
    productId = productId,
    quantity = quantity,
    paymentMethod = paymentMethod, //convert to enum
    orderStatus = orderStatus, //convert to enum
    deliveryMethod = deliveryMethod, //convert to enum
    customerId = customerId,
    courierId = courierId,
    accepted = accepted,
    updatedAt = updatedAt,
    createdAt = createdAt,

    product = null,
    customer = null,
    courier = null
)