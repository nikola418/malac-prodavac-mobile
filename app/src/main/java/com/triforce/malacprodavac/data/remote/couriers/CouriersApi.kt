package com.triforce.malacprodavac.data.remote.couriers

import com.triforce.malacprodavac.data.remote.couriers.dto.CreateCourierDto
import com.triforce.malacprodavac.data.remote.dto.PaginationResponse
import com.triforce.malacprodavac.domain.model.Courier
import com.triforce.malacprodavac.domain.model.Order
import com.triforce.malacprodavac.domain.model.couriers.UpdateCourier
import com.triforce.malacprodavac.domain.model.pagination.PaginationResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
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

    @PATCH("$ROUTE/{id}")
    suspend fun updateCourier(@Path("id") courierId: Int, @Body body: UpdateCourier): Courier

    @GET("${ROUTE}/{id}/orders")
    suspend fun getCourierOrders(@Path("id") id: Int): PaginationResponse<Order>

    companion object {
        const val ROUTE = "/couriers"
    }
}