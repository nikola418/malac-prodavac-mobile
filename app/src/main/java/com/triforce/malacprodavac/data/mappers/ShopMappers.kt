package com.triforce.malacprodavac.data.mappers

import com.triforce.malacprodavac.data.local.shops.ShopEntity
import com.triforce.malacprodavac.data.mappers.products.toProduct
import com.triforce.malacprodavac.data.mappers.users.toUser
import com.triforce.malacprodavac.domain.model.shops.Shop
import com.triforce.malacprodavac.domain.model.shops.UpdateShop

fun ShopEntity.toShop(): Shop = Shop(
    id = id,
    userId = userId,
    openFromDays = openFromDays,
    openTillDays = openTillDays,
    availableAtLatitude = availableAtLatitude,
    availableAtLongitude = availableAtLongitude,
    businessName = businessName,
    openFrom = openFrom,
    openTill = openTill,
    availableAt = availableAt,
    updatedAt = updatedAt,
    createdAt = createdAt,
    user = user?.toUser(),
    products = products.map {
        it.toProduct()
    },
    isFavored = isFavored,
    counts = null
)

fun Shop.toShopEntity(): ShopEntity = ShopEntity(
    id = id,
    userId = userId,
    businessName = businessName,
    openFromDays = openFromDays,
    openTillDays = openTillDays,
    openFrom = openFrom,
    openTill = openTill,
    availableAt = availableAt,
    availableAtLatitude = availableAtLatitude,
    availableAtLongitude = availableAtLongitude,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isFavored = isFavored
)

fun Shop.toUpdateShop(): UpdateShop = UpdateShop(
    businessName = businessName,
    availableAt = availableAt,
    availableAtLongitude = availableAtLongitude,
    availableAtLatitude = availableAtLatitude,
    openTillDays = openTillDays,
    openFromDays = openFromDays,
    openTill = openTill,
    openFrom = openFrom
)