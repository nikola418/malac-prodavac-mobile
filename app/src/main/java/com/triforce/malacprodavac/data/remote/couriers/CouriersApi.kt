package com.triforce.malacprodavac.data.remote.couriers

import com.triforce.malacprodavac.data.remote.couriers.dto.CreateCourierDto
import com.triforce.malacprodavac.domain.model.Courier
import com.triforce.malacprodavac.domain.model.pagination.PaginationResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface CouriersApi {
    @POST(ROUTE)
    suspend fun registerCouriers(
        @Body registerRequest: CreateCourierDto
    ): Courier

    @GET(ROUTE)
    suspend fun getCouriers(@QueryMap queryMap: MutableMap<String, String> = mutableMapOf()): PaginationResult<Courier>

    @GET("$ROUTE/{id}")
    suspend fun getCourier(@Path("id") courierId: Int): Courier

    companion object {
        const val ROUTE = "/couriers"
    }
}