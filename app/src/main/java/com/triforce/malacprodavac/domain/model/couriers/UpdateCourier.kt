package com.triforce.malacprodavac.domain.model.couriers

import com.triforce.malacprodavac.domain.model.users.UpdateUser

data class UpdateCourier(
    val user: UpdateUser? = null,
    val routeStartLatitude: Double? = null,
    val routeStartLongitude: Double? = null,
    val routeEndLatitude: Double? = null,
    val routeEndLongitude: Double? = null
)
