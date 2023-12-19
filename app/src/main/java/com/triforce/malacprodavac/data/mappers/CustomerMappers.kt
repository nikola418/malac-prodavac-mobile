package com.triforce.malacprodavac.data.mappers

import com.triforce.malacprodavac.data.local.customers.CustomerEntity
import com.triforce.malacprodavac.data.mappers.users.toUser
import com.triforce.malacprodavac.domain.model.Customer
import com.triforce.malacprodavac.domain.model.customers.UpdateCustomer

fun CustomerEntity.toCustomer(): Customer = Customer(
    id = id,
    userId = userId,
    createdAt = createdAt,
    updatedAt = updatedAt,
    user = user?.toUser()
)

fun Customer.toCustomerEntity(): CustomerEntity = CustomerEntity(
    id = id,
    userId = userId,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Customer.toUpdateCustomer(): UpdateCustomer = UpdateCustomer(
    
)