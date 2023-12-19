package com.triforce.malacprodavac.domain.model.shops

import com.triforce.malacprodavac.domain.model.users.UpdateUser

data class UpdateShop(
    val user: UpdateUser? = null,
    val businessName: String? = null,
    val openFrom: String? = null,
    val openTill: String? = null,
    val openFromDays: String? = null,
    val openTillDays: String? = null,
    val availableAt: String? = null,
    val availableAtLatitude: Double? = null,
    val availableAtLongitude: Double? = null
)
