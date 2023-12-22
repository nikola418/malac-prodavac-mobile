package com.triforce.malacprodavac.data.remote.orders.dto

import com.triforce.malacprodavac.domain.model.UpdateOrder

data class UpdateOrderDto(
    override val orderStatus: String?,
    override val accepted: Boolean?,
    override val courierId: Int?
) : UpdateOrder