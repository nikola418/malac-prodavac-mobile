package com.triforce.malacprodavac.data.mappers

import com.triforce.malacprodavac.data.local.couriers.CourierEntity
import com.triforce.malacprodavac.data.mappers.users.toUser
import com.triforce.malacprodavac.domain.model.Courier
import com.triforce.malacprodavac.domain.model.couriers.UpdateCourier

fun CourierEntity.toCourier(): Courier = Courier(
    id = id,
    userId = userId,
    routeStartLatitude = routeStartLatitude,
    routeStartLongitude = routeStartLongitude,
    routeEndLatitude = routeEndLatitude,
    routeEndLongitude = routeEndLongitude,
    createdAt = createdAt,
    updatedAt = updatedAt,

    user = user?.toUser()
)

fun Courier.toCourierEntity(): CourierEntity = CourierEntity(
    id = id,
    userId = userId,
    routeStartLatitude = routeStartLatitude,
    routeStartLongitude = routeStartLongitude,
    routeEndLatitude = routeEndLatitude,
    routeEndLongitude = routeEndLongitude,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun Courier.toUpdateCourier(): UpdateCourier = UpdateCourier(
    routeStartLatitude = routeStartLatitude,
    routeStartLongitude = routeStartLongitude,
    routeEndLatitude = routeEndLatitude,
    routeEndLongitude = routeEndLongitude
)

