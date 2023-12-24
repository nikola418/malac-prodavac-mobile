package com.triforce.malacprodavac.data.remote.orders

import com.triforce.malacprodavac.data.local.scheduledPickup.ScheduledPickupEntity
import com.triforce.malacprodavac.data.remote.dto.PaginationResponse
import com.triforce.malacprodavac.data.remote.orders.dto.CreateOrderDto
import com.triforce.malacprodavac.data.remote.orders.dto.CreateSchedulePickupDto
import com.triforce.malacprodavac.data.remote.orders.dto.UpdateOrderDto
import com.triforce.malacprodavac.data.remote.orders.dto.UpdateScheduledPickupDto
import com.triforce.malacprodavac.domain.model.Order
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderApi {

    @POST(ROUTE)
    suspend fun create(@Body() createPostDto: CreateOrderDto): Order

    @GET(ROUTE)
    suspend fun getOrders(): PaginationResponse<Order>

    @GET("${ROUTE}/{id}")
    suspend fun getOrder(@Path("id") id: Int): Order

    @PATCH("${ROUTE}/{id}")
    suspend fun update(
        @Path("id") id: Int,
        @Body() updateProductDto: UpdateOrderDto
    ): Order

    @DELETE("${ROUTE}/{id}")
    suspend fun delete(
        @Path("id") id: Int
    ): Order

    @POST("${ROUTE}/{id}/scheduledPickups")
    suspend fun insertScheduledPickup(
        @Path("id") id: Int,
        @Body() CreateSchedulePickupDto: CreateSchedulePickupDto
    ): ScheduledPickupEntity

    @PATCH("${ROUTE}/{id}/scheduledPickups/{scheduledPickupId}")
    suspend fun updateSchedulePickups(
        @Path("id") id: Int,
        @Path("scheduledPickupId") scheduledPickupId: Int,
        @Body() updateScheduledDto: UpdateScheduledPickupDto
    ): ScheduledPickupEntity


    companion object {
        const val ROUTE = "/orders"
    }
}