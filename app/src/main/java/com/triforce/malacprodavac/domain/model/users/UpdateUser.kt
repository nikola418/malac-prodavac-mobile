package com.triforce.malacprodavac.domain.model.users

data class UpdateUser(
    val currency: String? = null,
    val paymentMethod: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val password: String? = null,
    val address: String? = null,
    var addressLatitude: Double? = null,
    var addressLongitude: Double? = null,
    val phoneNumber: String? = null
)
