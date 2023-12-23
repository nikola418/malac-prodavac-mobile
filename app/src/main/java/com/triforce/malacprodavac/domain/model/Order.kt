package com.triforce.malacprodavac.domain.model

import com.triforce.malacprodavac.domain.model.products.Product

data class Order(
    val quantity: Double,
    val paymentMethod: String, //convert to enum
    var orderStatus: String, //convert to enum
    val deliveryMethod: String, //convert to enum
    val id: Int,
    val productId: Int,
    val customerId: Int,
    var courierId: Int?,
    val accepted: Boolean,
    val updatedAt: String,
    val createdAt: String,
    val product: Product?,
    val customer: Customer?,
    val courier: Courier?,
)
