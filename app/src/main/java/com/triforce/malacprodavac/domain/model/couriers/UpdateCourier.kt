package com.triforce.malacprodavac.domain.model.couriers

import com.triforce.malacprodavac.domain.model.users.UpdateUser

data class UpdateCourier(
    val user: UpdateUser? = null,
    var routeStartLatitude: Double? = null,
    var routeStartLongitude: Double? = null,
    var routeEndLatitude: Double? = null,
    var routeEndLongitude: Double? = null
)
