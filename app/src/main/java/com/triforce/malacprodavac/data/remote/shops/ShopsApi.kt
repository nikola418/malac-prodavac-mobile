package com.triforce.malacprodavac.data.remote.shops

import com.triforce.malacprodavac.data.local.scheduledPickup.ScheduledPickupEntity
import com.triforce.malacprodavac.data.remote.dto.PaginationResponse
import com.triforce.malacprodavac.data.remote.shops.dto.CreateShopDto
import com.triforce.malacprodavac.domain.model.shops.Shop
import com.triforce.malacprodavac.domain.model.shops.UpdateShop
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ShopsApi {
    @POST(ROUTE)
    suspend fun registerShop(@Body registerRequest: CreateShopDto): Shop

    @GET(ROUTE)
    suspend fun getShops(@QueryMap() queryMap: MutableMap<String, String>): PaginationResponse<Shop>

    @GET("${ROUTE}/{id}")
    suspend fun getShop(@Path("id") id: Int): Shop

    @GET("${ROUTE}/{id}/scheduledPickups")
    suspend fun getShopSchedulePickups(
        @Path("id") shopId: Int,
        //@QueryMap queryMap: MutableMap<String, String>
    ): PaginationResponse<ScheduledPickupEntity>

    @GET("${ROUTE}/{id}/scheduledPickups/{scheduledPickupId}")
    suspend fun getShopSchedulePickupsById(
        @Path("id") shopId: Int,
        @Path("scheduledPickupId") scheduledPickupId: Int
        //@QueryMap queryMap: MutableMap<String, String>
    ): ScheduledPickupEntity

    @PATCH("$ROUTE/{id}")
    suspend fun updateShop(@Path("id") shopId: Int, @Body dto: UpdateShop): Shop

    companion object {
        const val ROUTE = "/shops"
    }
}