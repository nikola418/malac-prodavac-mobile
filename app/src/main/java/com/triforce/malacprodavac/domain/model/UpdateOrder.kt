package com.triforce.malacprodavac.domain.model

interface UpdateOrder {
    val orderStatus: String?
    val accepted: Boolean?
    val courierId: Int?
}