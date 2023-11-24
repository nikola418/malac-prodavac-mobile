package com.triforce.malacprodavac.domain.repository

import com.triforce.malacprodavac.domain.model.CreateShop
import com.triforce.malacprodavac.domain.model.Product
import com.triforce.malacprodavac.domain.model.Shop
import com.triforce.malacprodavac.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ShopRepository {
    suspend fun registerShop(
        createShop: CreateShop
    ): Flow<Resource<Shop>>

    suspend fun getShops(
        fetchFromRemote: Boolean,
        queryMap: MutableMap<String, String>
    ): Flow<Resource<List<Shop>>>

    suspend fun getShop(
        id: Int,
        fetchFromRemote: Boolean,
    ): Flow<Resource<Shop>>

}