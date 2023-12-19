package com.triforce.malacprodavac.data.mappers.users

import com.triforce.malacprodavac.data.local.user.UserEntity
import com.triforce.malacprodavac.data.local.user.relations.UserWithRelations
import com.triforce.malacprodavac.data.mappers.toCourier
import com.triforce.malacprodavac.data.mappers.toCourierEntity
import com.triforce.malacprodavac.data.mappers.toCustomer
import com.triforce.malacprodavac.data.mappers.toCustomerEntity
import com.triforce.malacprodavac.data.mappers.toShop
import com.triforce.malacprodavac.data.mappers.toShopEntity
import com.triforce.malacprodavac.data.mappers.users.userMedias.toUserMedia
import com.triforce.malacprodavac.data.mappers.users.userMedias.toUserMediaEntity
import com.triforce.malacprodavac.domain.model.User
import com.triforce.malacprodavac.domain.model.users.UpdateUser

fun UserEntity.toUser(): User = User(
    id = id,
    firstName = firstName,
    lastName = lastName,
    email = email,
    address = address,
    phoneNumber = phoneNumber ?: "",
    addressLatitude = addressLatitude,
    addressLongitude = addressLongitude,
    paymentMethod = paymentMethod,
    roles = roles,
    currency = currency,
    createdAt = createdAt,
    updatedAt = updatedAt,
    profilePicture = profilePicture?.toUserMedia(),
    customer = customer?.toCustomer(),
    courier = courier?.toCourier(),
    shop = shop?.toShop(),
)

fun User.toUserEntity(): UserEntity = UserEntity(
    id = id,
    firstName = firstName,
    lastName = lastName,
    email = email,
    address = address,
    phoneNumber = phoneNumber ?: "",
    addressLatitude = addressLatitude,
    addressLongitude = addressLongitude,
    paymentMethod = paymentMethod,
    roles = roles,
    currency = currency,
    createdAt = createdAt,
    updatedAt = updatedAt,
)


fun UserWithRelations.toUser(): User = User(
    id = user.id,
    firstName = user.firstName,
    lastName = user.lastName,
    email = user.email,
    address = user.address,
    phoneNumber = user.phoneNumber ?: "",
    addressLatitude = user.addressLatitude,
    addressLongitude = user.addressLongitude,
    paymentMethod = user.paymentMethod,
    roles = user.roles,
    currency = user.currency,
    createdAt = user.createdAt,
    updatedAt = user.updatedAt,
    profilePicture = profilePicture?.toUserMedia(),
    customer = customer?.toCustomer(),
    courier = courier?.toCourier(),
    shop = shop?.toShop()
)

fun User.toUserWithRelations(): UserWithRelations = UserWithRelations(
    user = UserEntity(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        address = address,
        phoneNumber = phoneNumber ?: "",
        addressLatitude = addressLatitude,
        addressLongitude = addressLongitude,
        paymentMethod = paymentMethod,
        roles = roles,
        currency = currency,
        createdAt = createdAt,
        updatedAt = updatedAt,
    ),
    profilePicture = profilePicture?.toUserMediaEntity(),
    customer = customer?.toCustomerEntity(),
    courier = courier?.toCourierEntity(),
    shop = shop?.toShopEntity()
)

fun User.toUpdateUser(): UpdateUser = UpdateUser(
    firstName = firstName,
    lastName = lastName,
    address = address,
    addressLatitude = addressLatitude,
    addressLongitude = addressLongitude,
    phoneNumber = phoneNumber,
    currency = currency,
    paymentMethod = paymentMethod,
    email = email,
    password = null
)
