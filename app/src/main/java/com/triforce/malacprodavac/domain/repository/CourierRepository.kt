package com.triforce.malacprodavac.domain.repository

import com.triforce.malacprodavac.domain.model.Courier
import com.triforce.malacprodavac.domain.model.CreateCourier
import com.triforce.malacprodavac.domain.model.couriers.UpdateCourier
import com.triforce.malacprodavac.domain.model.pagination.PaginationResult
import com.triforce.malacprodavac.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface CourierRepository {
    suspend fun registerCourier(
        createCourier: CreateCourier
    ): Flow<Resource<Courier>>

    suspend fun getCouriers(
        filters: MutableMap<String, String> = mutableMapOf()
    ): Flow<Resource<PaginationResult<Courier>>>

    suspend fun getCourier(
        courierId: Int
    ): Flow<Resource<Courier>>

    suspend fun updateCourier(
        courierId: Int,
        dto: UpdateCourier
    ): Flow<Resource<Courier>>
}