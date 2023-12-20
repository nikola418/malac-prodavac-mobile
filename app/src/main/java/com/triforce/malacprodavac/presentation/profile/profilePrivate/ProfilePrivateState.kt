package com.triforce.malacprodavac.presentation.profile.profilePrivate

import android.net.Uri
import com.triforce.malacprodavac.data.mappers.toUpdateCourier
import com.triforce.malacprodavac.data.mappers.toUpdateCustomer
import com.triforce.malacprodavac.data.mappers.toUpdateShop
import com.triforce.malacprodavac.data.mappers.users.toUpdateUser
import com.triforce.malacprodavac.domain.model.User
import com.triforce.malacprodavac.domain.model.couriers.UpdateCourier
import com.triforce.malacprodavac.domain.model.customers.UpdateCustomer
import com.triforce.malacprodavac.domain.model.shops.UpdateShop
import com.triforce.malacprodavac.domain.model.users.UpdateUser

data class ProfilePrivateState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = true,
    val currentUser: User? = null,
    val profileImageUrl: String? = null,
    val profileImageKey: String? = null,
    val token: String? = null,
    var mediaUri: Uri? = null,
    var newImage: Boolean = false,
    var isEditing: Boolean = false,

    val updateUser: UpdateUser? = currentUser?.toUpdateUser(),
    val updateCustomer: UpdateCustomer? = currentUser?.customer?.toUpdateCustomer(),
    val updateCourier: UpdateCourier? = currentUser?.courier?.toUpdateCourier(),
    val updateShop: UpdateShop? = currentUser?.shop?.toUpdateShop(),

    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val addressError: String? = null,
    val phoneNumberError: String? = null,
    val businessNameError: String? = null
)