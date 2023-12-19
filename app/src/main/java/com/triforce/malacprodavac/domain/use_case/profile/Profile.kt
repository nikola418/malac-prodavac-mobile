package com.triforce.malacprodavac.domain.use_case.profile

import com.triforce.malacprodavac.domain.use_case.GetToken
import com.triforce.malacprodavac.domain.use_case.auth.IsAuthenticated
import com.triforce.malacprodavac.domain.use_case.couriers.UpdateCourierUseCase
import com.triforce.malacprodavac.domain.use_case.customers.UpdateCustomerUseCase
import com.triforce.malacprodavac.domain.use_case.login.Me
import com.triforce.malacprodavac.domain.use_case.shops.UpdateShopUseCase

class Profile(
    val getMe: Me,
    val logout: Logout,
    val getToken: GetToken,
    val setProfilePicture: SetProfilePicture,
    val isAuthenticated: IsAuthenticated,
    val updateCustomer: UpdateCustomerUseCase,
    val updateCourier: UpdateCourierUseCase,
    val updateShop: UpdateShopUseCase
)